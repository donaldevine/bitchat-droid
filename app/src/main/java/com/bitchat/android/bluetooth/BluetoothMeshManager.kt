package com.bitchat.android.bluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattServer
import android.bluetooth.BluetoothGattServerCallback
import android.bluetooth.BluetoothGattService
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.bluetooth.le.BluetoothLeAdvertiser
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.pm.PackageManager
import android.os.ParcelUuid
import android.util.Log
import androidx.core.app.ActivityCompat
import com.bitchat.android.data.model.Message
import com.bitchat.android.data.model.Peer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BluetoothMeshManager @Inject constructor(
    private val context: Context
) {
    
    companion object {
        private const val TAG = "BluetoothMeshManager"
        
        // BitChat service UUID
        val BITCHAT_SERVICE_UUID: UUID = UUID.fromString("6E400001-B5A3-F393-E0A9-E50E24DCCA9E")
        val MESSAGE_CHARACTERISTIC_UUID: UUID = UUID.fromString("6E400002-B5A3-F393-E0A9-E50E24DCCA9E")
        val PEER_INFO_CHARACTERISTIC_UUID: UUID = UUID.fromString("6E400003-B5A3-F393-E0A9-E50E24DCCA9E")
        
        private const val MAX_MESSAGE_SIZE = 512 // Maximum BLE characteristic size
    }
    
    private val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter = bluetoothManager.adapter
    private val bluetoothLeScanner: BluetoothLeScanner? = bluetoothAdapter?.bluetoothLeScanner
    private val bluetoothLeAdvertiser: BluetoothLeAdvertiser? = bluetoothAdapter?.bluetoothLeAdvertiser
    
    private var gattServer: BluetoothGattServer? = null
    private val connectedDevices = mutableMapOf<String, BluetoothGatt>()
    
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    // State flows
    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()
    
    private val _isAdvertising = MutableStateFlow(false)
    val isAdvertising: StateFlow<Boolean> = _isAdvertising.asStateFlow()
    
    private val _discoveredPeers = MutableStateFlow<List<Peer>>(emptyList())
    val discoveredPeers: StateFlow<List<Peer>> = _discoveredPeers.asStateFlow()
    
    private val _receivedMessages = MutableSharedFlow<Message>()
    val receivedMessages: SharedFlow<Message> = _receivedMessages.asSharedFlow()
    
    private val _connectionEvents = MutableSharedFlow<ConnectionEvent>()
    val connectionEvents: SharedFlow<ConnectionEvent> = _connectionEvents.asSharedFlow()
    
    // Scan callback
    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            handleScanResult(result)
        }
        
        override fun onBatchScanResults(results: MutableList<ScanResult>) {
            super.onBatchScanResults(results)
            results.forEach { handleScanResult(it) }
        }
        
        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            Log.e(TAG, "Scan failed with error code: $errorCode")
            _isScanning.value = false
        }
    }
    
    // Advertise callback
    private val advertiseCallback = object : AdvertiseCallback() {
        override fun onStartSuccess(settingsInEffect: AdvertiseSettings?) {
            super.onStartSuccess(settingsInEffect)
            Log.d(TAG, "Advertising started successfully")
            _isAdvertising.value = true
        }
        
        override fun onStartFailure(errorCode: Int) {
            super.onStartFailure(errorCode)
            Log.e(TAG, "Advertising failed with error code: $errorCode")
            _isAdvertising.value = false
        }
    }
    
    // GATT server callback
    private val gattServerCallback = object : BluetoothGattServerCallback() {
        override fun onConnectionStateChange(device: BluetoothDevice?, status: Int, newState: Int) {
            super.onConnectionStateChange(device, status, newState)
            device?.let { 
                when (newState) {
                    BluetoothProfile.STATE_CONNECTED -> {
                        Log.d(TAG, "Device connected: ${it.address}")
                        scope.launch {
                            _connectionEvents.emit(ConnectionEvent.Connected(it.address))
                        }
                    }
                    BluetoothProfile.STATE_DISCONNECTED -> {
                        Log.d(TAG, "Device disconnected: ${it.address}")
                        connectedDevices.remove(it.address)
                        scope.launch {
                            _connectionEvents.emit(ConnectionEvent.Disconnected(it.address))
                        }
                    }
                }
            }
        }
        
        override fun onCharacteristicWriteRequest(
            device: BluetoothDevice?,
            requestId: Int,
            characteristic: BluetoothGattCharacteristic?,
            preparedWrite: Boolean,
            responseNeeded: Boolean,
            offset: Int,
            value: ByteArray?
        ) {
            super.onCharacteristicWriteRequest(device, characteristic, preparedWrite, responseNeeded, offset, value)
            
            device?.let { bluetoothDevice ->
                value?.let { data ->
                    when (characteristic?.uuid) {
                        MESSAGE_CHARACTERISTIC_UUID -> {
                            handleReceivedMessage(data, bluetoothDevice.address)
                        }
                        PEER_INFO_CHARACTERISTIC_UUID -> {
                            handleReceivedPeerInfo(data, bluetoothDevice.address)
                        }
                    }
                }
            }
            
            if (responseNeeded) {
                gattServer?.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, null)
            }
        }
    }
    
    fun initialize(): Boolean {
        if (!bluetoothAdapter.isEnabled) {
            Log.w(TAG, "Bluetooth is not enabled")
            return false
        }
        
        if (!hasRequiredPermissions()) {
            Log.w(TAG, "Missing required Bluetooth permissions")
            return false
        }
        
        setupGattServer()
        return true
    }
    
    @SuppressLint("MissingPermission")
    private fun setupGattServer() {
        gattServer = bluetoothManager.openGattServer(context, gattServerCallback)
        
        val service = BluetoothGattService(BITCHAT_SERVICE_UUID, BluetoothGattService.SERVICE_TYPE_PRIMARY)
        
        val messageCharacteristic = BluetoothGattCharacteristic(
            MESSAGE_CHARACTERISTIC_UUID,
            BluetoothGattCharacteristic.PROPERTY_WRITE or BluetoothGattCharacteristic.PROPERTY_READ,
            BluetoothGattCharacteristic.PERMISSION_WRITE or BluetoothGattCharacteristic.PERMISSION_READ
        )
        
        val peerInfoCharacteristic = BluetoothGattCharacteristic(
            PEER_INFO_CHARACTERISTIC_UUID,
            BluetoothGattCharacteristic.PROPERTY_WRITE or BluetoothGattCharacteristic.PROPERTY_READ,
            BluetoothGattCharacteristic.PERMISSION_WRITE or BluetoothGattCharacteristic.PERMISSION_READ
        )
        
        service.addCharacteristic(messageCharacteristic)
        service.addCharacteristic(peerInfoCharacteristic)
        
        gattServer?.addService(service)
    }
    
    @SuppressLint("MissingPermission")
    fun startScanning() {
        if (!hasRequiredPermissions() || _isScanning.value) return
        
        bluetoothLeScanner?.let { scanner ->
            val scanFilter = ScanFilter.Builder()
                .setServiceUuid(ParcelUuid(BITCHAT_SERVICE_UUID))
                .build()
            
            val scanSettings = ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
                .build()
            
            scanner.startScan(listOf(scanFilter), scanSettings, scanCallback)
            _isScanning.value = true
            Log.d(TAG, "Started scanning for BitChat devices")
        }
    }
    
    @SuppressLint("MissingPermission")
    fun stopScanning() {
        if (!hasRequiredPermissions()) return
        
        bluetoothLeScanner?.stopScan(scanCallback)
        _isScanning.value = false
        Log.d(TAG, "Stopped scanning")
    }
    
    @SuppressLint("MissingPermission")
    fun startAdvertising() {
        if (!hasRequiredPermissions() || _isAdvertising.value) return
        
        bluetoothLeAdvertiser?.let { advertiser ->
            val advertiseSettings = AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
                .setConnectable(true)
                .build()
            
            val advertiseData = AdvertiseData.Builder()
                .setIncludeDeviceName(true)
                .setIncludeTxPowerLevel(false)
                .addServiceUuid(ParcelUuid(BITCHAT_SERVICE_UUID))
                .build()
            
            advertiser.startAdvertising(advertiseSettings, advertiseData, advertiseCallback)
            Log.d(TAG, "Started advertising BitChat service")
        }
    }
    
    @SuppressLint("MissingPermission")
    fun stopAdvertising() {
        if (!hasRequiredPermissions()) return
        
        bluetoothLeAdvertiser?.stopAdvertising(advertiseCallback)
        _isAdvertising.value = false
        Log.d(TAG, "Stopped advertising")
    }
    
    private fun handleScanResult(result: ScanResult) {
        val device = result.device
        val rssi = result.rssi
        
        // Create or update peer information
        val peer = Peer(
            id = device.address,
            name = device.name ?: "Unknown Device",
            publicKey = ByteArray(0), // Will be exchanged during connection
            bluetoothAddress = device.address,
            lastSeen = System.currentTimeMillis(),
            isOnline = true,
            rssi = rssi
        )
        
        // Update discovered peers list
        val currentPeers = _discoveredPeers.value.toMutableList()
        val existingIndex = currentPeers.indexOfFirst { it.id == peer.id }
        
        if (existingIndex >= 0) {
            currentPeers[existingIndex] = peer
        } else {
            currentPeers.add(peer)
        }
        
        _discoveredPeers.value = currentPeers
        
        // Attempt to connect to the device
        connectToDevice(device)
    }
    
    @SuppressLint("MissingPermission")
    private fun connectToDevice(device: BluetoothDevice) {
        if (connectedDevices.containsKey(device.address)) return
        
        val gatt = device.connectGatt(context, false, object : BluetoothGattCallback() {
            override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
                super.onConnectionStateChange(gatt, status, newState)
                
                when (newState) {
                    BluetoothProfile.STATE_CONNECTED -> {
                        Log.d(TAG, "Connected to ${device.address}")
                        gatt?.let { 
                            connectedDevices[device.address] = it
                            it.discoverServices()
                        }
                    }
                    BluetoothProfile.STATE_DISCONNECTED -> {
                        Log.d(TAG, "Disconnected from ${device.address}")
                        connectedDevices.remove(device.address)
                        gatt?.close()
                    }
                }
            }
            
            override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
                super.onServicesDiscovered(gatt, status)
                
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    Log.d(TAG, "Services discovered for ${device.address}")
                    // Services are ready for communication
                }
            }
        })
    }
    
    private fun handleReceivedMessage(data: ByteArray, senderAddress: String) {
        try {
            // Deserialize the message
            val messageJson = String(data, Charsets.UTF_8)
            // TODO: Implement message deserialization
            Log.d(TAG, "Received message from $senderAddress: $messageJson")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to process received message", e)
        }
    }
    
    private fun handleReceivedPeerInfo(data: ByteArray, senderAddress: String) {
        try {
            // Process peer information exchange
            val peerInfoJson = String(data, Charsets.UTF_8)
            Log.d(TAG, "Received peer info from $senderAddress: $peerInfoJson")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to process received peer info", e)
        }
    }
    
    @SuppressLint("MissingPermission")
    fun sendMessage(message: Message, targetAddress: String? = null) {
        if (!hasRequiredPermissions()) return
        
        try {
            // Serialize message
            val messageData = message.toString().toByteArray(Charsets.UTF_8)
            
            if (messageData.size > MAX_MESSAGE_SIZE) {
                Log.w(TAG, "Message too large, chunking required")
                // TODO: Implement message chunking
                return
            }
            
            if (targetAddress != null) {
                // Send to specific peer
                connectedDevices[targetAddress]?.let { gatt ->
                    val service = gatt.getService(BITCHAT_SERVICE_UUID)
                    val characteristic = service?.getCharacteristic(MESSAGE_CHARACTERISTIC_UUID)
                    characteristic?.let {
                        it.value = messageData
                        gatt.writeCharacteristic(it)
                    }
                }
            } else {
                // Broadcast to all connected peers
                connectedDevices.values.forEach { gatt ->
                    val service = gatt.getService(BITCHAT_SERVICE_UUID)
                    val characteristic = service?.getCharacteristic(MESSAGE_CHARACTERISTIC_UUID)
                    characteristic?.let {
                        it.value = messageData
                        gatt.writeCharacteristic(it)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to send message", e)
        }
    }
    
    private fun hasRequiredPermissions(): Boolean {
        val requiredPermissions = arrayOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH_ADVERTISE,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        
        return requiredPermissions.all { permission ->
            ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
    }
    
    fun cleanup() {
        stopScanning()
        stopAdvertising()
        
        connectedDevices.values.forEach { gatt ->
            gatt.disconnect()
            gatt.close()
        }
        connectedDevices.clear()
        
        gattServer?.close()
        gattServer = null
    }
}

sealed class ConnectionEvent {
    data class Connected(val address: String) : ConnectionEvent()
    data class Disconnected(val address: String) : ConnectionEvent()
}

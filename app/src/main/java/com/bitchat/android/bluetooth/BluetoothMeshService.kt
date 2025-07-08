package com.bitchat.android.bluetooth

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.bitchat.android.MainActivity
import com.bitchat.android.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BluetoothMeshService : Service() {
    
    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "bitchat_mesh_channel"
        private const val CHANNEL_NAME = "BitChat Mesh Network"
        
        const val ACTION_START_MESH = "com.bitchat.android.START_MESH"
        const val ACTION_STOP_MESH = "com.bitchat.android.STOP_MESH"
    }
    
    @Inject
    lateinit var bluetoothMeshManager: BluetoothMeshManager
    
    private val binder = MeshServiceBinder()
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    private var isRunning = false
    
    inner class MeshServiceBinder : Binder() {
        fun getService(): BluetoothMeshService = this@BluetoothMeshService
    }
    
    override fun onBind(intent: Intent?): IBinder {
        return binder
    }
    
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START_MESH -> startMeshNetworking()
            ACTION_STOP_MESH -> stopMeshNetworking()
        }
        
        return START_STICKY
    }
    
    private fun startMeshNetworking() {
        if (isRunning) return
        
        val notification = createNotification()
        startForeground(NOTIFICATION_ID, notification)
        
        serviceScope.launch {
            if (bluetoothMeshManager.initialize()) {
                bluetoothMeshManager.startAdvertising()
                bluetoothMeshManager.startScanning()
                isRunning = true
                
                // Monitor connection events
                bluetoothMeshManager.connectionEvents.collect { event ->
                    handleConnectionEvent(event)
                }
            }
        }
    }
    
    private fun stopMeshNetworking() {
        if (!isRunning) return
        
        bluetoothMeshManager.cleanup()
        isRunning = false
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }
    
    private fun handleConnectionEvent(event: ConnectionEvent) {
        when (event) {
            is ConnectionEvent.Connected -> {
                updateNotification("Connected to ${event.address}")
            }
            is ConnectionEvent.Disconnected -> {
                updateNotification("Mesh network active")
            }
        }
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "BitChat mesh network service"
                setShowBadge(false)
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("BitChat")
            .setContentText("Mesh network active")
            .setSmallIcon(R.drawable.ic_bluetooth)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setSilent(true)
            .build()
    }
    
    private fun updateNotification(text: String) {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("BitChat")
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_bluetooth)
            .setOngoing(true)
            .setSilent(true)
            .build()
        
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        bluetoothMeshManager.cleanup()
    }
    
    fun getMeshManager(): BluetoothMeshManager = bluetoothMeshManager
}

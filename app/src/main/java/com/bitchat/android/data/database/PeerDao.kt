package com.bitchat.android.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bitchat.android.data.model.Peer
import com.bitchat.android.data.model.TrustLevel
import kotlinx.coroutines.flow.Flow

@Dao
interface PeerDao {
    
    @Query("SELECT * FROM peers ORDER BY name ASC")
    fun getAllPeers(): Flow<List<Peer>>
    
    @Query("SELECT * FROM peers WHERE isOnline = 1 ORDER BY lastSeen DESC")
    fun getOnlinePeers(): Flow<List<Peer>>
    
    @Query("SELECT * FROM peers WHERE id = :peerId")
    suspend fun getPeerById(peerId: String): Peer?
    
    @Query("SELECT * FROM peers WHERE bluetoothAddress = :address")
    suspend fun getPeerByBluetoothAddress(address: String): Peer?
    
    @Query("SELECT * FROM peers WHERE isBlocked = 0 AND trustLevel != 'UNTRUSTED' ORDER BY routeQuality DESC, routeHopCount ASC")
    suspend fun getTrustedPeers(): List<Peer>
    
    @Query("SELECT * FROM peers WHERE isBlocked = 0 AND routeHopCount < :maxHops ORDER BY routeQuality DESC")
    suspend fun getReachablePeers(maxHops: Int): List<Peer>
    
    @Query("UPDATE peers SET isOnline = 0")
    suspend fun markAllPeersOffline()
    
    @Query("UPDATE peers SET isOnline = 1, lastSeen = :timestamp WHERE id = :peerId")
    suspend fun markPeerOnline(peerId: String, timestamp: Long = System.currentTimeMillis())
    
    @Query("UPDATE peers SET isOnline = 0, lastSeen = :timestamp WHERE id = :peerId")
    suspend fun markPeerOffline(peerId: String, timestamp: Long = System.currentTimeMillis())
    
    @Query("UPDATE peers SET rssi = :rssi, lastSeen = :timestamp WHERE id = :peerId")
    suspend fun updatePeerSignalStrength(peerId: String, rssi: Int, timestamp: Long = System.currentTimeMillis())
    
    @Query("UPDATE peers SET batteryLevel = :batteryLevel WHERE id = :peerId")
    suspend fun updatePeerBatteryLevel(peerId: String, batteryLevel: Int)
    
    @Query("UPDATE peers SET trustLevel = :trustLevel WHERE id = :peerId")
    suspend fun updatePeerTrustLevel(peerId: String, trustLevel: TrustLevel)
    
    @Query("UPDATE peers SET isBlocked = :isBlocked WHERE id = :peerId")
    suspend fun updatePeerBlockedStatus(peerId: String, isBlocked: Boolean)
    
    @Query("UPDATE peers SET routeHopCount = :hopCount, routeQuality = :quality WHERE id = :peerId")
    suspend fun updatePeerRoute(peerId: String, hopCount: Int, quality: Double)
    
    @Query("DELETE FROM peers WHERE lastSeen < :threshold AND isOnline = 0")
    suspend fun cleanupStaleEntries(threshold: Long)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPeer(peer: Peer)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPeers(peers: List<Peer>)
    
    @Update
    suspend fun updatePeer(peer: Peer)
    
    @Delete
    suspend fun deletePeer(peer: Peer)
}

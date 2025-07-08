package com.bitchat.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * Represents a peer device in the mesh network
 */
@Entity(tableName = "peers")
@Serializable
data class Peer(
    @PrimaryKey
    val id: String,
    val name: String,
    val publicKey: ByteArray,
    val bluetoothAddress: String,
    val lastSeen: Long = System.currentTimeMillis(),
    val isOnline: Boolean = false,
    val rssi: Int = -100, // Signal strength
    val batteryLevel: Int? = null,
    val deviceInfo: String? = null,
    val trustLevel: TrustLevel = TrustLevel.UNKNOWN,
    val isBlocked: Boolean = false,
    val routeHopCount: Int = Int.MAX_VALUE, // Number of hops to reach this peer
    val routeQuality: Double = 0.0 // Route quality score (0.0 to 1.0)
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Peer

        if (id != other.id) return false
        if (name != other.name) return false
        if (!publicKey.contentEquals(other.publicKey)) return false
        if (bluetoothAddress != other.bluetoothAddress) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + publicKey.contentHashCode()
        result = 31 * result + bluetoothAddress.hashCode()
        return result
    }
}

@Serializable
enum class TrustLevel {
    UNKNOWN,
    UNTRUSTED,
    TRUSTED,
    VERIFIED
}

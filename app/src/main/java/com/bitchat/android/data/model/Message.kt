package com.bitchat.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.util.UUID

/**
 * Represents a message in the BitChat system
 */
@Entity(tableName = "messages")
@Serializable
data class Message(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val senderId: String,
    val recipientId: String? = null, // null for broadcast messages
    val channelId: String? = null,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val messageType: MessageType = MessageType.TEXT,
    val encryptedContent: ByteArray? = null,
    val signature: ByteArray? = null,
    val isDelivered: Boolean = false,
    val isRead: Boolean = false,
    val hopCount: Int = 0,
    val maxHops: Int = 5,
    val ttl: Long = System.currentTimeMillis() + (24 * 60 * 60 * 1000), // 24 hours
    val priority: MessagePriority = MessagePriority.NORMAL
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Message

        if (id != other.id) return false
        if (senderId != other.senderId) return false
        if (recipientId != other.recipientId) return false
        if (channelId != other.channelId) return false
        if (content != other.content) return false
        if (timestamp != other.timestamp) return false
        if (messageType != other.messageType) return false
        if (encryptedContent != null) {
            if (other.encryptedContent == null) return false
            if (!encryptedContent.contentEquals(other.encryptedContent)) return false
        } else if (other.encryptedContent != null) return false
        if (signature != null) {
            if (other.signature == null) return false
            if (!signature.contentEquals(other.signature)) return false
        } else if (other.signature != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + senderId.hashCode()
        result = 31 * result + (recipientId?.hashCode() ?: 0)
        result = 31 * result + (channelId?.hashCode() ?: 0)
        result = 31 * result + content.hashCode()
        result = 31 * result + timestamp.hashCode()
        result = 31 * result + messageType.hashCode()
        result = 31 * result + (encryptedContent?.contentHashCode() ?: 0)
        result = 31 * result + (signature?.contentHashCode() ?: 0)
        return result
    }
}

@Serializable
enum class MessageType {
    TEXT,
    IMAGE,
    FILE,
    SYSTEM,
    ACKNOWLEDGMENT,
    ROUTE_DISCOVERY,
    HEARTBEAT
}

@Serializable
enum class MessagePriority {
    LOW,
    NORMAL,
    HIGH,
    URGENT
}

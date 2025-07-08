package com.bitchat.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.util.UUID

/**
 * Represents a communication channel
 */
@Entity(tableName = "channels")
@Serializable
data class Channel(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String? = null,
    val isPrivate: Boolean = false,
    val encryptionKey: ByteArray? = null,
    val createdBy: String,
    val createdAt: Long = System.currentTimeMillis(),
    val memberCount: Int = 0,
    val lastMessageTime: Long = 0,
    val lastMessageId: String? = null,
    val isArchived: Boolean = false,
    val color: String? = null, // Hex color for UI
    val icon: String? = null // Icon identifier
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Channel

        if (id != other.id) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (isPrivate != other.isPrivate) return false
        if (encryptionKey != null) {
            if (other.encryptionKey == null) return false
            if (!encryptionKey.contentEquals(other.encryptionKey)) return false
        } else if (other.encryptionKey != null) return false
        if (createdBy != other.createdBy) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + isPrivate.hashCode()
        result = 31 * result + (encryptionKey?.contentHashCode() ?: 0)
        result = 31 * result + createdBy.hashCode()
        return result
    }
}

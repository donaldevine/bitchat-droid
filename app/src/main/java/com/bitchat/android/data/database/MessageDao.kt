package com.bitchat.android.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bitchat.android.data.model.Message
import com.bitchat.android.data.model.MessageType
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    
    @Query("SELECT * FROM messages ORDER BY timestamp DESC")
    fun getAllMessages(): Flow<List<Message>>
    
    @Query("SELECT * FROM messages WHERE channelId = :channelId ORDER BY timestamp ASC")
    fun getMessagesForChannel(channelId: String): Flow<List<Message>>
    
    @Query("SELECT * FROM messages WHERE (senderId = :peerId OR recipientId = :peerId) AND channelId IS NULL ORDER BY timestamp ASC")
    fun getDirectMessagesWithPeer(peerId: String): Flow<List<Message>>
    
    @Query("SELECT * FROM messages WHERE id = :messageId")
    suspend fun getMessageById(messageId: String): Message?
    
    @Query("SELECT * FROM messages WHERE ttl > :currentTime AND isDelivered = 0")
    suspend fun getPendingMessages(currentTime: Long = System.currentTimeMillis()): List<Message>
    
    @Query("SELECT * FROM messages WHERE messageType = :type ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getMessagesByType(type: MessageType, limit: Int = 100): List<Message>
    
    @Query("DELETE FROM messages WHERE ttl < :currentTime")
    suspend fun deleteExpiredMessages(currentTime: Long = System.currentTimeMillis()): Int
    
    @Query("UPDATE messages SET isDelivered = 1 WHERE id = :messageId")
    suspend fun markAsDelivered(messageId: String)
    
    @Query("UPDATE messages SET isRead = 1 WHERE id = :messageId")
    suspend fun markAsRead(messageId: String)
    
    @Query("UPDATE messages SET isRead = 1 WHERE channelId = :channelId")
    suspend fun markChannelMessagesAsRead(channelId: String)
    
    @Query("UPDATE messages SET isRead = 1 WHERE (senderId = :peerId OR recipientId = :peerId) AND channelId IS NULL")
    suspend fun markDirectMessagesAsRead(peerId: String)
    
    @Query("SELECT COUNT(*) FROM messages WHERE channelId = :channelId AND isRead = 0")
    fun getUnreadCountForChannel(channelId: String): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM messages WHERE (senderId = :peerId OR recipientId = :peerId) AND channelId IS NULL AND isRead = 0")
    fun getUnreadCountForPeer(peerId: String): Flow<Int>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: Message)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<Message>)
    
    @Update
    suspend fun updateMessage(message: Message)
    
    @Delete
    suspend fun deleteMessage(message: Message)
    
    @Query("DELETE FROM messages WHERE channelId = :channelId")
    suspend fun deleteMessagesForChannel(channelId: String)
}

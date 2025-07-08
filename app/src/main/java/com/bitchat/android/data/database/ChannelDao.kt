package com.bitchat.android.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bitchat.android.data.model.Channel
import kotlinx.coroutines.flow.Flow

@Dao
interface ChannelDao {
    
    @Query("SELECT * FROM channels WHERE isArchived = 0 ORDER BY lastMessageTime DESC, name ASC")
    fun getActiveChannels(): Flow<List<Channel>>
    
    @Query("SELECT * FROM channels WHERE isArchived = 1 ORDER BY name ASC")
    fun getArchivedChannels(): Flow<List<Channel>>
    
    @Query("SELECT * FROM channels WHERE id = :channelId")
    suspend fun getChannelById(channelId: String): Channel?
    
    @Query("SELECT * FROM channels WHERE name = :name")
    suspend fun getChannelByName(name: String): Channel?
    
    @Query("SELECT * FROM channels WHERE isPrivate = 0 ORDER BY memberCount DESC, name ASC")
    fun getPublicChannels(): Flow<List<Channel>>
    
    @Query("UPDATE channels SET memberCount = :count WHERE id = :channelId")
    suspend fun updateMemberCount(channelId: String, count: Int)
    
    @Query("UPDATE channels SET lastMessageTime = :timestamp, lastMessageId = :messageId WHERE id = :channelId")
    suspend fun updateLastMessage(channelId: String, timestamp: Long, messageId: String)
    
    @Query("UPDATE channels SET isArchived = :isArchived WHERE id = :channelId")
    suspend fun updateArchivedStatus(channelId: String, isArchived: Boolean)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChannel(channel: Channel)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChannels(channels: List<Channel>)
    
    @Update
    suspend fun updateChannel(channel: Channel)
    
    @Delete
    suspend fun deleteChannel(channel: Channel)
}

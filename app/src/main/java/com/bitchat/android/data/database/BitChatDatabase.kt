package com.bitchat.android.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import android.content.Context
import com.bitchat.android.data.model.Channel
import com.bitchat.android.data.model.Message
import com.bitchat.android.data.model.MessagePriority
import com.bitchat.android.data.model.MessageType
import com.bitchat.android.data.model.Peer
import com.bitchat.android.data.model.TrustLevel

@Database(
    entities = [Message::class, Peer::class, Channel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(BitChatTypeConverters::class)
abstract class BitChatDatabase : RoomDatabase() {
    
    abstract fun messageDao(): MessageDao
    abstract fun peerDao(): PeerDao
    abstract fun channelDao(): ChannelDao
    
    companion object {
        const val DATABASE_NAME = "bitchat_database"
        
        @Volatile
        private var INSTANCE: BitChatDatabase? = null
        
        fun getInstance(context: Context): BitChatDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BitChatDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class BitChatTypeConverters {
    
    @TypeConverter
    fun fromByteArray(value: ByteArray?): String? {
        return value?.let { android.util.Base64.encodeToString(it, android.util.Base64.DEFAULT) }
    }
    
    @TypeConverter
    fun toByteArray(value: String?): ByteArray? {
        return value?.let { android.util.Base64.decode(it, android.util.Base64.DEFAULT) }
    }
    
    @TypeConverter
    fun fromMessageType(value: MessageType): String {
        return value.name
    }
    
    @TypeConverter
    fun toMessageType(value: String): MessageType {
        return MessageType.valueOf(value)
    }
    
    @TypeConverter
    fun fromMessagePriority(value: MessagePriority): String {
        return value.name
    }
    
    @TypeConverter
    fun toMessagePriority(value: String): MessagePriority {
        return MessagePriority.valueOf(value)
    }
    
    @TypeConverter
    fun fromTrustLevel(value: TrustLevel): String {
        return value.name
    }
    
    @TypeConverter
    fun toTrustLevel(value: String): TrustLevel {
        return TrustLevel.valueOf(value)
    }
}

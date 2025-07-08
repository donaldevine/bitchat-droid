package com.bitchat.android.di

import android.content.Context
import androidx.room.Room
import com.bitchat.android.data.database.BitChatDatabase
import com.bitchat.android.data.database.ChannelDao
import com.bitchat.android.data.database.MessageDao
import com.bitchat.android.data.database.PeerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideBitChatDatabase(@ApplicationContext context: Context): BitChatDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            BitChatDatabase::class.java,
            BitChatDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    
    @Provides
    fun provideMessageDao(database: BitChatDatabase): MessageDao {
        return database.messageDao()
    }
    
    @Provides
    fun providePeerDao(database: BitChatDatabase): PeerDao {
        return database.peerDao()
    }
    
    @Provides
    fun provideChannelDao(database: BitChatDatabase): ChannelDao {
        return database.channelDao()
    }
}

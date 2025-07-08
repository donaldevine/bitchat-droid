package com.bitchat.android.di

import android.content.Context
import com.bitchat.android.bluetooth.BluetoothMeshManager
import com.bitchat.android.crypto.CryptographyManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    @Provides
    @Singleton
    fun provideBluetoothMeshManager(@ApplicationContext context: Context): BluetoothMeshManager {
        return BluetoothMeshManager(context)
    }
    
    @Provides
    @Singleton
    fun provideCryptographyManager(): CryptographyManager {
        return CryptographyManager()
    }
}

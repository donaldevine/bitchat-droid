package com.bitchat.android.di;

import android.content.Context;
import com.bitchat.android.bluetooth.BluetoothMeshManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class NetworkModule_ProvideBluetoothMeshManagerFactory implements Factory<BluetoothMeshManager> {
  private final Provider<Context> contextProvider;

  public NetworkModule_ProvideBluetoothMeshManagerFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public BluetoothMeshManager get() {
    return provideBluetoothMeshManager(contextProvider.get());
  }

  public static NetworkModule_ProvideBluetoothMeshManagerFactory create(
      Provider<Context> contextProvider) {
    return new NetworkModule_ProvideBluetoothMeshManagerFactory(contextProvider);
  }

  public static BluetoothMeshManager provideBluetoothMeshManager(Context context) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideBluetoothMeshManager(context));
  }
}

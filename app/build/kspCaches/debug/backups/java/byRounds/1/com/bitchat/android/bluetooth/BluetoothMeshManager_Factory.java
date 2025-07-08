package com.bitchat.android.bluetooth;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
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
public final class BluetoothMeshManager_Factory implements Factory<BluetoothMeshManager> {
  private final Provider<Context> contextProvider;

  public BluetoothMeshManager_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public BluetoothMeshManager get() {
    return newInstance(contextProvider.get());
  }

  public static BluetoothMeshManager_Factory create(Provider<Context> contextProvider) {
    return new BluetoothMeshManager_Factory(contextProvider);
  }

  public static BluetoothMeshManager newInstance(Context context) {
    return new BluetoothMeshManager(context);
  }
}

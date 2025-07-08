package com.bitchat.android.bluetooth;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class BluetoothMeshService_MembersInjector implements MembersInjector<BluetoothMeshService> {
  private final Provider<BluetoothMeshManager> bluetoothMeshManagerProvider;

  public BluetoothMeshService_MembersInjector(
      Provider<BluetoothMeshManager> bluetoothMeshManagerProvider) {
    this.bluetoothMeshManagerProvider = bluetoothMeshManagerProvider;
  }

  public static MembersInjector<BluetoothMeshService> create(
      Provider<BluetoothMeshManager> bluetoothMeshManagerProvider) {
    return new BluetoothMeshService_MembersInjector(bluetoothMeshManagerProvider);
  }

  @Override
  public void injectMembers(BluetoothMeshService instance) {
    injectBluetoothMeshManager(instance, bluetoothMeshManagerProvider.get());
  }

  @InjectedFieldSignature("com.bitchat.android.bluetooth.BluetoothMeshService.bluetoothMeshManager")
  public static void injectBluetoothMeshManager(BluetoothMeshService instance,
      BluetoothMeshManager bluetoothMeshManager) {
    instance.bluetoothMeshManager = bluetoothMeshManager;
  }
}

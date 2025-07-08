package com.bitchat.android.di;

import com.bitchat.android.crypto.CryptographyManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class NetworkModule_ProvideCryptographyManagerFactory implements Factory<CryptographyManager> {
  @Override
  public CryptographyManager get() {
    return provideCryptographyManager();
  }

  public static NetworkModule_ProvideCryptographyManagerFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CryptographyManager provideCryptographyManager() {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideCryptographyManager());
  }

  private static final class InstanceHolder {
    private static final NetworkModule_ProvideCryptographyManagerFactory INSTANCE = new NetworkModule_ProvideCryptographyManagerFactory();
  }
}

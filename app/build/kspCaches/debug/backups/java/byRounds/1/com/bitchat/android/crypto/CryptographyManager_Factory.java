package com.bitchat.android.crypto;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class CryptographyManager_Factory implements Factory<CryptographyManager> {
  @Override
  public CryptographyManager get() {
    return newInstance();
  }

  public static CryptographyManager_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CryptographyManager newInstance() {
    return new CryptographyManager();
  }

  private static final class InstanceHolder {
    private static final CryptographyManager_Factory INSTANCE = new CryptographyManager_Factory();
  }
}

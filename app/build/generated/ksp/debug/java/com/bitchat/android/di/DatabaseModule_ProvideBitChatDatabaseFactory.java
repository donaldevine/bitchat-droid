package com.bitchat.android.di;

import android.content.Context;
import com.bitchat.android.data.database.BitChatDatabase;
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
public final class DatabaseModule_ProvideBitChatDatabaseFactory implements Factory<BitChatDatabase> {
  private final Provider<Context> contextProvider;

  public DatabaseModule_ProvideBitChatDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public BitChatDatabase get() {
    return provideBitChatDatabase(contextProvider.get());
  }

  public static DatabaseModule_ProvideBitChatDatabaseFactory create(
      Provider<Context> contextProvider) {
    return new DatabaseModule_ProvideBitChatDatabaseFactory(contextProvider);
  }

  public static BitChatDatabase provideBitChatDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideBitChatDatabase(context));
  }
}

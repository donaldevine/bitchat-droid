package com.bitchat.android.di;

import com.bitchat.android.data.database.BitChatDatabase;
import com.bitchat.android.data.database.PeerDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class DatabaseModule_ProvidePeerDaoFactory implements Factory<PeerDao> {
  private final Provider<BitChatDatabase> databaseProvider;

  public DatabaseModule_ProvidePeerDaoFactory(Provider<BitChatDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public PeerDao get() {
    return providePeerDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvidePeerDaoFactory create(
      Provider<BitChatDatabase> databaseProvider) {
    return new DatabaseModule_ProvidePeerDaoFactory(databaseProvider);
  }

  public static PeerDao providePeerDao(BitChatDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.providePeerDao(database));
  }
}

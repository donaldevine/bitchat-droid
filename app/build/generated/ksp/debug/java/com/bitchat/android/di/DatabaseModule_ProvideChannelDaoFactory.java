package com.bitchat.android.di;

import com.bitchat.android.data.database.BitChatDatabase;
import com.bitchat.android.data.database.ChannelDao;
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
public final class DatabaseModule_ProvideChannelDaoFactory implements Factory<ChannelDao> {
  private final Provider<BitChatDatabase> databaseProvider;

  public DatabaseModule_ProvideChannelDaoFactory(Provider<BitChatDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ChannelDao get() {
    return provideChannelDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideChannelDaoFactory create(
      Provider<BitChatDatabase> databaseProvider) {
    return new DatabaseModule_ProvideChannelDaoFactory(databaseProvider);
  }

  public static ChannelDao provideChannelDao(BitChatDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideChannelDao(database));
  }
}

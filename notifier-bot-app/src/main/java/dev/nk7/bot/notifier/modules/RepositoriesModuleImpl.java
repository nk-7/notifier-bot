package dev.nk7.bot.notifier.modules;

import dev.nk7.bot.notifier.ApplicationProperties;
import dev.nk7.bot.notifier.core.port.out.repository.ChatRepository;
import dev.nk7.bot.notifier.persistence.repository.RocksChatRepository;
import dev.nk7.bot.notifier.persistence.rocksdb.Rocks;
import dev.nk7.bot.notifier.persistence.rocksdb.RocksProperties;
import dev.nk7.bot.notifier.persistence.serialization.JacksonSerializer;
import dev.nk7.bot.notifier.persistence.serialization.Serializer;

import java.util.Objects;

class RepositoriesModuleImpl implements RepositoriesModule {
  private final Rocks rocks;
  private final Serializer serializer;

  RepositoriesModuleImpl(ApplicationProperties applicationProperties) {
    Objects.requireNonNull(applicationProperties);
    Objects.requireNonNull(applicationProperties.getRocksDbPath());
    this.rocks = rocks(applicationProperties.getRocksDbPath());
    this.serializer = serializer();
  }

  public ChatRepository chatRepository() {
    return new RocksChatRepository(rocks, serializer);
  }

  private Rocks rocks(String path) {
    return new Rocks(new RocksProperties(path));
  }

  private Serializer serializer() {
    return new JacksonSerializer();
  }

}

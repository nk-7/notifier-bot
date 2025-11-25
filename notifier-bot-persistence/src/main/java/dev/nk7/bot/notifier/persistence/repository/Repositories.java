package dev.nk7.bot.notifier.persistence.repository;

import dev.nk7.bot.notifier.core.port.out.ChatRepository;
import dev.nk7.bot.notifier.core.port.out.NotificationRepository;
import dev.nk7.bot.notifier.persistence.rocksdb.Entities;
import dev.nk7.bot.notifier.persistence.rocksdb.Rocks;
import dev.nk7.bot.notifier.persistence.serialization.JacksonSerializer;

import java.util.Objects;

public class Repositories {

  private final Rocks rocks;

  public Repositories(Rocks rocks) {
    this.rocks = Objects.requireNonNull(rocks);
  }

  public ChatRepository chatRepository() {
    return new RocksChatRepository(rocks, new JacksonSerializer());
  }

  public NotificationRepository notificationRepository() {
    return new RocksNotificationRepository(rocks, new JacksonSerializer());

  }
}

package dev.nk7.bot.notifier.persistence.repository;

import dev.nk7.bot.notifier.core.model.Notification;
import dev.nk7.bot.notifier.core.port.out.repository.NotificationRepository;
import dev.nk7.bot.notifier.persistence.rocksdb.Entities;
import dev.nk7.bot.notifier.persistence.rocksdb.Rocks;
import dev.nk7.bot.notifier.persistence.rocksdb.RocksRepository;
import dev.nk7.bot.notifier.persistence.serialization.Serializer;

public class RocksNotificationRepository extends RocksRepository implements NotificationRepository {

  public RocksNotificationRepository(Rocks rocks, Serializer serializer) {
    super(rocks, serializer);
  }

  @Override
  public void save(Notification notification) {
    final byte[] key = serializer.toBytes(notification.id());
    final byte[] value = serializer.toBytes(notification);
    rocks.put(Entities.NOTIFICATION, key, value);
  }
}

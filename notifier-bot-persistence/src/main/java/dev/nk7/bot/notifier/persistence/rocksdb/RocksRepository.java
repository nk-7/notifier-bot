package dev.nk7.bot.notifier.persistence.rocksdb;

import dev.nk7.bot.notifier.persistence.serialization.Serializer;

import java.util.Objects;

public abstract class RocksRepository<T extends Enum<T>> {


  protected final Rocks<T> rocks;

  protected final Serializer serializer;

  protected RocksRepository(Rocks<T> rocks, Serializer serializer) {
    this.rocks = Objects.requireNonNull(rocks);
    this.serializer = Objects.requireNonNull(serializer);
  }
}

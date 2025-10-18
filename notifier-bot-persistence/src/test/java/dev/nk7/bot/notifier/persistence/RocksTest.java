package dev.nk7.bot.notifier.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.nk7.bot.notifier.persistence.rocksdb.Entities;
import dev.nk7.bot.notifier.persistence.rocksdb.Rocks;
import dev.nk7.bot.notifier.persistence.rocksdb.RocksProperties;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.rocksdb.RocksDBException;

class RocksTest {


  private final Rocks<Entities> rocks;


  RocksTest() throws RocksDBException {
    this.rocks = new Rocks<>(new RocksProperties("./rocksdb"), Entities.class);
  }


  @Test
  void testRocksDB() throws RocksDBException, JsonProcessingException {
    rocks.put(Entities.CHAT, "abc".getBytes(), "def".getBytes());
    final byte[] value = rocks.get(Entities.CHAT, "abc".getBytes());
    Assertions.assertThat(value).isEqualTo("def".getBytes());

  }
}

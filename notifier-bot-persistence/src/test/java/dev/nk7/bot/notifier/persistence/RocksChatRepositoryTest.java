package dev.nk7.bot.notifier.persistence;

import dev.nk7.bot.notifier.core.model.Chat;
import dev.nk7.bot.notifier.core.model.ChatStatus;
import dev.nk7.bot.notifier.persistence.rocksdb.Entities;
import dev.nk7.bot.notifier.persistence.rocksdb.Rocks;
import dev.nk7.bot.notifier.persistence.rocksdb.RocksProperties;
import dev.nk7.bot.notifier.persistence.serialization.JacksonSerializer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

class RocksChatRepositoryTest {

  final RocksChatRepository repository = new RocksChatRepository(
    new Rocks<Entities>(new RocksProperties("./test"), Entities.class),
    new JacksonSerializer());


  @Test
  void name() {
    final Chat chat = new Chat(1L, "1", "TEST", ChatStatus.NEW, Collections.emptySet());
    repository.save(chat);
    Assertions.assertThat(repository.findByChatId(chat.chatId())).isPresent().contains(chat);
  }
}


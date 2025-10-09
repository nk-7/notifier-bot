package dev.nk7.bot.notifier.repository.r2dbc;

import dev.nk7.bot.notifier.database.Migrator;
import dev.nk7.bot.notifier.entities.Chat;
import dev.nk7.bot.notifier.entities.ChatStatus;
import io.r2dbc.h2.H2ConnectionFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.ExecutionException;


class R2dbcChatRepositoryTest {

  public static final String CHANGE_LOG = "db.changelog/db.changelog-master.yaml";
  private R2dbcChatRepository chatRepository;


  @BeforeAll
  static void beforeAll() {
    new Migrator(CHANGE_LOG, "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "").migrate();
  }

  @BeforeEach
  void setUp() {
    chatRepository = new R2dbcChatRepository(H2ConnectionFactory.inMemory("testdb", "sa", ""));

  }

  @Test
  void saveAndFindByChatId() throws ExecutionException, InterruptedException {
    final Chat newChat = new Chat(null, 123L, null, "private", ChatStatus.NEW);
    final Chat savedChat = chatRepository.save(newChat).get();
    final Optional<Chat> found = chatRepository.findByChatId(savedChat.chatId()).get();
    Assertions.assertThat(found).isNotEmpty().contains(savedChat);
  }

  @Test
  void findUnexistentChatId() throws ExecutionException, InterruptedException {
    final Optional<Chat> found = chatRepository.findByChatId(-1L).get();
    Assertions.assertThat(found).isEmpty();

  }
}

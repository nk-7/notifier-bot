package r2dbc;

import dev.nk7.bot.notifier.database.Migrator;
import dev.nk7.bot.notifier.persistence.repository.r2dbc.R2dbcChatRepository;
import dev.nk7.bot.notifier.persistence.table.ChatNewRow;
import dev.nk7.bot.notifier.persistence.table.ChatRow;
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
  void saveNewAndFindByChatId() throws ExecutionException, InterruptedException {
    final ChatNewRow newRow = new ChatNewRow(123L, null, "private", "NEW");
    final ChatRow savedChat = chatRepository.saveNew(newRow).get();
    final Optional<ChatRow> found = chatRepository.findByChatId(savedChat.chatId()).get();
    Assertions.assertThat(found).isNotEmpty().contains(savedChat);
  }

  @Test
  void findUnexistentChatId() throws ExecutionException, InterruptedException {
    final Optional<ChatRow> found = chatRepository.findByChatId(-1L).get();
    Assertions.assertThat(found).isEmpty();

  }
}

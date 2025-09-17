package dev.nk7.bot.notifier.repository;

import dev.nk7.bot.notifier.database.Migrator;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

class ChatRepositoryTest {

  public static final String CHANGE_LOG = "db.changelog/db.changelog-master.yaml";
  private ConnectionFactory connectionFactory;

  @BeforeAll
  static void beforeAll() {
    new Migrator(CHANGE_LOG, "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "").migrate();
  }

  @BeforeEach
  void setUp() {
    connectionFactory = H2ConnectionFactory.inMemory("testdb", "sa", "");

  }

  @Test
  void save() {
    final Result result = Flux.from(connectionFactory.create())
      .map(c -> c.createStatement("select * from chat"))
      .flatMap(s -> Flux.from(s.execute()))
      .blockFirst();

    System.out.println(result);
  }
}

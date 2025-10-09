package dev.nk7.bot.notifier.persistence.repository.r2dbc;


import dev.nk7.bot.notifier.persistence.repository.ChatRepository;
import dev.nk7.bot.notifier.persistence.table.ChatNewRow;
import dev.nk7.bot.notifier.persistence.table.ChatRow;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class R2dbcChatRepository implements ChatRepository {
  private static final Logger log = LoggerFactory.getLogger(R2dbcChatRepository.class);
  private final ConnectionFactory connectionFactory;

  public R2dbcChatRepository(ConnectionFactory connectionFactory) {
    this.connectionFactory = Objects.requireNonNull(connectionFactory);
  }


  public CompletableFuture<ChatRow> saveNew(ChatNewRow chat) {
    log.debug("Сохранение в БД чата '{}'.", chat);
    return Mono.from(connectionFactory.create())
      .flatMap(c ->
        {
          final Statement statement = c.createStatement("""
            insert into chat(chat_id, status, title, type)
            values ($1, $2, $3, $4)
            """);
          statement.bind("$1", chat.chatId());
          statement.bind("$2", chat.status());

          if (chat.title() == null) {
            statement.bindNull("$3", String.class);
          } else {
            statement.bind("$3", chat.title());
          }

          statement.bind("$4", chat.type());
          statement.returnGeneratedValues("ID");
          return Mono.from(statement.execute())
            .doFinally(st -> c.close());
        }
      )
      .flatMap(row -> Mono.from(row.map((r, md) -> {
        final Long id = r.get("ID", Long.class);
        log.debug("Чат сохранен с ID = '{}'.", id);
        return new ChatRow(id, chat.chatId(), chat.title(), chat.type(), chat.status());
      })))
      .onErrorMap(RuntimeException::new)
      .toFuture();
  }

  @Override
  public CompletableFuture<Optional<ChatRow>> findByChatId(Long chatId) {
    log.debug("Поиск в БД чата с chat_id = '{}'.", chatId);
    return Mono.from(connectionFactory.create())
      .flatMap(c ->
        Mono.from(c.createStatement("select * from chat where chat_id = $1")
            .bind("$1", chatId)
            .execute())
          .doFinally((st) -> c.close())
      )
      .flatMap(row ->
        Mono.from(row.map((r, md) ->
        {
          log.debug("Чат с chat_id = '{}' найден.", chatId);
          return Optional.of(new ChatRow(
            r.get("ID", Long.class),
            r.get("CHAT_ID", Long.class),
            r.get("TITLE", String.class),
            r.get("TYPE", String.class),
            r.get("STATUS", String.class)));
        })))
      .defaultIfEmpty(Optional.empty())
      .toFuture();
  }
}

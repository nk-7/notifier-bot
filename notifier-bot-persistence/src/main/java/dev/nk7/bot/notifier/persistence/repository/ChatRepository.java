package dev.nk7.bot.notifier.persistence.repository;

import dev.nk7.bot.notifier.persistence.table.ChatNewRow;
import dev.nk7.bot.notifier.persistence.table.ChatRow;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ChatRepository {
  CompletableFuture<ChatRow> saveNew(ChatNewRow chat);

  CompletableFuture<Optional<ChatRow>> findByChatId(Long chatId);

}

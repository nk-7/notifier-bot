package dev.nk7.bot.notifier.repository;

import dev.nk7.bot.notifier.entities.Chat;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ChatRepository {
  CompletableFuture<Chat> save(Chat chat);

  CompletableFuture<Optional<Chat>> findByChatId(Long chatId);

}

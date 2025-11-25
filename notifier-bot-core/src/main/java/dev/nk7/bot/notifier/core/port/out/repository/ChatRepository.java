package dev.nk7.bot.notifier.core.port.out.repository;

import dev.nk7.bot.notifier.core.model.Chat;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public interface ChatRepository {
  void save(Chat chat);

  Optional<Chat> findByChatId(Long chatId);

  Set<Chat> getFiltered(Predicate<Chat> filter);

}

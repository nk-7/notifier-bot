package dev.nk7.bot.notifier.core.port.out;

import dev.nk7.bot.notifier.core.model.Chat;

import java.util.Optional;

public interface ChatRepository {
  void save(Chat chat);

  Optional<Chat> findByChatId(Long chatId);

}

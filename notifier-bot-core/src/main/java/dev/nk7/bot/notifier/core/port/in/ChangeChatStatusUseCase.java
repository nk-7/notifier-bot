package dev.nk7.bot.notifier.core.port.in;

import dev.nk7.bot.notifier.core.model.Chat;

public interface ChangeChatStatusUseCase {
  Chat change(Long chatId, String status);
}

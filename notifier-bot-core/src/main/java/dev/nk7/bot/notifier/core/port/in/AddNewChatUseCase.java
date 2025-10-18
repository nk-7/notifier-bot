package dev.nk7.bot.notifier.core.port.in;

import dev.nk7.bot.notifier.core.model.Chat;

public interface AddNewChatUseCase {

  Chat addNewChat(Long chatId, String title, String type);
}

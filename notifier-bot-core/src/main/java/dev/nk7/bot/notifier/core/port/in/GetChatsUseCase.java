package dev.nk7.bot.notifier.core.port.in;

import dev.nk7.bot.notifier.core.model.Chat;

import java.util.Set;

public interface GetChatsUseCase {

  Set<Chat> getChats();
}

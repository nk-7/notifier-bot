package dev.nk7.bot.notifier.core.usecase;

import dev.nk7.bot.notifier.core.model.Chat;
import dev.nk7.bot.notifier.core.port.out.repository.ChatRepository;

import java.util.Objects;
import java.util.Set;

public class GetChatsUseCase implements dev.nk7.bot.notifier.core.port.in.GetChatsUseCase {

  private final ChatRepository chatRepository;

  public GetChatsUseCase(ChatRepository chatRepository) {
    this.chatRepository = Objects.requireNonNull(chatRepository);
  }

  @Override
  public Set<Chat> getChats() {
    return chatRepository.getFiltered(c -> true);
  }
}

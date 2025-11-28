package dev.nk7.bot.notifier.core.usecase;

import dev.nk7.bot.notifier.core.model.Chat;
import dev.nk7.bot.notifier.core.model.ChatStatus;
import dev.nk7.bot.notifier.core.port.out.repository.ChatRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class ChangeChatUseCase implements dev.nk7.bot.notifier.core.port.in.ChangeChatUseCase {

  private final ChatRepository chatRepository;

  public ChangeChatUseCase(ChatRepository chatRepository) {
    this.chatRepository = Objects.requireNonNull(chatRepository);
  }

  @Override
  public Chat change(Long chatId, String status, Set<String> subscriptions) {
    final Optional<Chat> chatOpt = chatRepository.findByChatId(chatId);
    if (chatOpt.isEmpty()) {
      return null;
    }
    final ChatStatus chatStatus = mapStatus(status);
    if (chatStatus == null) {
      return null;
    }
    final Chat chat = chatOpt.get()
      .withStatus(chatStatus)
      .withSubscriptions(subscriptions);
    chatRepository.save(chat);
    return chat;
  }


  private ChatStatus mapStatus(String status) {
    return switch (status) {
      case "NEW" -> ChatStatus.NEW;
      case "APPROVED" -> ChatStatus.APPROVED;
      default -> null;
    };

  }
}

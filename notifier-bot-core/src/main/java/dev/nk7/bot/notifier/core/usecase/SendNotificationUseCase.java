package dev.nk7.bot.notifier.core.usecase;

import dev.nk7.bot.notifier.core.model.Chat;
import dev.nk7.bot.notifier.core.port.out.repository.ChatRepository;
import dev.nk7.bot.notifier.core.port.out.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

public class SendNotificationUseCase implements dev.nk7.bot.notifier.core.port.in.SendNotificationUseCase {
  private static final Logger log = LoggerFactory.getLogger(SendNotificationUseCase.class);
  private final MessageService messageService;
  private final ChatRepository chatRepository;

  public SendNotificationUseCase(MessageService messageService, ChatRepository chatRepository) {
    this.messageService = Objects.requireNonNull(messageService);
    this.chatRepository = Objects.requireNonNull(chatRepository);
  }

  @Override
  public void sendNotification(String text, Set<String> tags) {
    final Set<Chat> chatsToBeNotified = chatRepository.getFiltered(approvedAndSubscribedToTag(tags));
    log.info("Найдено {} чатов для нотификации.", chatsToBeNotified.size());
    for (final Chat chat : chatsToBeNotified) {
      messageService.send(chat.chatId().toString(), text);
    }
  }

  private Predicate<Chat> approvedAndSubscribedToTag(Set<String> tags) {
    return c -> c.isApproved() && c.subscribed(tags);
  }
}

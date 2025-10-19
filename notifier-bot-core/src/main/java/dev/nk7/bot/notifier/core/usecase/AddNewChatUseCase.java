package dev.nk7.bot.notifier.core.usecase;

import dev.nk7.bot.notifier.core.model.Chat;
import dev.nk7.bot.notifier.core.model.ChatStatus;
import dev.nk7.bot.notifier.core.port.out.ChatRepository;
import dev.nk7.bot.notifier.core.port.out.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class AddNewChatUseCase implements dev.nk7.bot.notifier.core.port.in.AddNewChatUseCase {

  private static final Logger log = LoggerFactory.getLogger(AddNewChatUseCase.class);

  private final ChatRepository chatRepository;

  private final MessageService messageService;

  public AddNewChatUseCase(ChatRepository chatRepository, MessageService messageService) {
    this.chatRepository = Objects.requireNonNull(chatRepository);
    this.messageService = Objects.requireNonNull(messageService);
  }

  @Override
  public Chat addNewChat(Long chatId, String title, String type) {
    final Optional<Chat> chat = chatRepository.findByChatId(chatId);
    if (chat.isPresent()) {
      log.debug("Chat with id {} already exists", chatId);
      messageService.send(chatId.toString(), "Бот уже знает о Вас.");
      return chat.get();
    }
    final Chat newChat = new Chat(chatId, title, type, ChatStatus.APPROVED, Set.of("test"));
    chatRepository.save(newChat);
    log.debug("Created new chat with id {}", chatId);
    messageService.send(chatId.toString(), "Добро пожаловать в бот для получения уведомлений!");
    return newChat;
  }
}

package dev.nk7.bot.notifier.core.usecase;

import dev.nk7.bot.notifier.core.model.Chat;
import dev.nk7.bot.notifier.core.model.ChatStatus;
import dev.nk7.bot.notifier.core.port.out.ChatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

public class AddNewChatUseCase implements dev.nk7.bot.notifier.core.port.in.AddNewChatUseCase {

  private static final Logger log = LoggerFactory.getLogger(AddNewChatUseCase.class);

  private final ChatRepository chatRepository;

  public AddNewChatUseCase(ChatRepository chatRepository) {
    this.chatRepository = Objects.requireNonNull(chatRepository);
  }

  @Override
  public Chat addNewChat(Long chatId, String title, String type) {
    final Optional<Chat> chat = chatRepository.findByChatId(chatId);
    if (chat.isPresent()) {
      log.debug("Chat with id {} already exists", chatId);
      return chat.get();
    }
    final Chat newChat = new Chat(chatId, title, type, ChatStatus.NEW, Collections.emptySet());
    chatRepository.save(newChat);
    log.debug("Created new chat with id {}", chatId);
    return newChat;
  }
}

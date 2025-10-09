package dev.nk7.bot.notifier.service.imp;

import dev.nk7.bot.notifier.entities.Chat;
import dev.nk7.bot.notifier.entities.ChatStatus;
import dev.nk7.bot.notifier.repository.ChatRepository;
import dev.nk7.bot.notifier.service.ChatService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public class ChatServiceImp implements ChatService {

  private final ChatRepository chatRepository;

  public ChatServiceImp(ChatRepository chatRepository) {
    this.chatRepository = Objects.requireNonNull(chatRepository);
  }

  @Override
  public CompletableFuture<Chat> newChat(Long chatId, String type, String title) {
    return chatRepository.findByChatId(chatId)
      .thenComposeAsync(o ->
        o.map(CompletableFuture::completedFuture)
          .orElseGet(() -> chatRepository.save(new Chat(null, chatId, title, type, ChatStatus.NEW)))
      );
  }
}

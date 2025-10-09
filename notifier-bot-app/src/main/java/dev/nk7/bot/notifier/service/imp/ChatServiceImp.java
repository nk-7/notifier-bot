package dev.nk7.bot.notifier.service.imp;

import dev.nk7.bot.notifier.model.Chat;
import dev.nk7.bot.notifier.model.ChatStatus;
import dev.nk7.bot.notifier.persistence.repository.ChatRepository;
import dev.nk7.bot.notifier.persistence.table.ChatNewRow;
import dev.nk7.bot.notifier.persistence.table.ChatRow;
import dev.nk7.bot.notifier.service.ChatService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class ChatServiceImp implements ChatService {

  private final ChatRepository chatRepository;

  public ChatServiceImp(ChatRepository chatRepository) {
    this.chatRepository = Objects.requireNonNull(chatRepository);
  }

  @Override
  public CompletableFuture<Chat> newChat(Long chatId, String type, String title) {
    final CompletableFuture<Optional<ChatRow>> byChatId = chatRepository.findByChatId(chatId);

    return byChatId
      .thenComposeAsync(o ->
        o.map(row -> CompletableFuture.completedFuture(map(row)))
          .orElseGet(() -> saveNew(chatId, title, type, ChatStatus.NEW.name()))
      );
  }

  private CompletableFuture<Chat> saveNew(Long chatId, String type, String title, String status) {
    return chatRepository.saveNew(new ChatNewRow(chatId, type, title, status))
      .thenApply(row -> new Chat(row.id(), row.chatId(), row.title(), row.type(), map(row.status())));
  }


  private Chat map(ChatRow chatRow) {
    return new Chat(
      chatRow.id(),
      chatRow.chatId(),
      chatRow.title(),
      chatRow.type(),
      map(chatRow.status())
    );
  }

  private ChatStatus map(String chatStatus) {
    return ChatStatus.valueOf(chatStatus);
  }

}

package dev.nk7.bot.notifier.handler;

import dev.nk7.bot.notifier.api.ChatDto;
import dev.nk7.bot.notifier.core.model.Chat;
import dev.nk7.bot.notifier.core.port.in.GetChatsUseCase;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

public class GetChatsHandler extends UseCaseHandler<GetChatsUseCase> {
  public GetChatsHandler(GetChatsUseCase useCase) {
    super(useCase);
  }

  @Override
  public void handle(@NotNull Context ctx) {
    final Set<Chat> chats = useCase().getChats();
    final Set<ChatDto> chatDtoSet = chats.stream()
      .map(c -> new ChatDto(
        c.chatId(),
        c.title(),
        c.type(),
        c.status().name(),
        c.subscriptions())
      ).collect(Collectors.toSet());
    ctx.contentType("application/json");
    ctx.json(chatDtoSet);
  }
}

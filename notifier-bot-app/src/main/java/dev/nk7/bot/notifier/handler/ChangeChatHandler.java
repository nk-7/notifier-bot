package dev.nk7.bot.notifier.handler;

import dev.nk7.bot.notifier.api.ChangeChatRequest;
import dev.nk7.bot.notifier.api.ChangeChatResponse;
import dev.nk7.bot.notifier.core.model.Chat;
import dev.nk7.bot.notifier.core.port.in.ChangeChatUseCase;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

public class ChangeChatHandler extends UseCaseHandler<ChangeChatUseCase> {
  public ChangeChatHandler(ChangeChatUseCase useCase) {
    super(useCase);
  }

  @Override
  public void handle(@NotNull Context ctx) {
    final Long chatId = ctx.pathParamAsClass("chatId", Long.class).get();
    final ChangeChatRequest changeChatRequest = ctx.bodyAsClass(ChangeChatRequest.class);
    final Chat changedChat = useCase().change(chatId, changeChatRequest.status(), changeChatRequest.subscriptions());
    final ChangeChatResponse response = new ChangeChatResponse(
      chatId, changedChat.title(), changedChat.type(), changedChat.status().name(), changedChat.subscriptions()
    );
    ctx.contentType("application/json");
    ctx.json(response);
  }
}

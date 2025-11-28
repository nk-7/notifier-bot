package dev.nk7.bot.notifier.handler;

import dev.nk7.bot.notifier.api.ChangeChatStatusRequest;
import dev.nk7.bot.notifier.api.ChangeChatStatusResponse;
import dev.nk7.bot.notifier.core.model.Chat;
import dev.nk7.bot.notifier.core.port.in.ChangeChatStatusUseCase;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

public class ChangeChatStatusHandler extends UseCaseHandler<ChangeChatStatusUseCase> {
  public ChangeChatStatusHandler(ChangeChatStatusUseCase useCase) {
    super(useCase);
  }

  @Override
  public void handle(@NotNull Context ctx) {
    final Long chatId = ctx.pathParamAsClass("chatId", Long.class).get();
    final ChangeChatStatusRequest changeChatStatusRequest = ctx.bodyAsClass(ChangeChatStatusRequest.class);
    final Chat changedChat = useCase().change(chatId, changeChatStatusRequest.status());
    final ChangeChatStatusResponse response = new ChangeChatStatusResponse(
      chatId, changedChat.title(), changedChat.type(), changedChat.status().name(), changedChat.subscriptions()
    );
    ctx.contentType("application/json");
    ctx.json(response);
  }
}

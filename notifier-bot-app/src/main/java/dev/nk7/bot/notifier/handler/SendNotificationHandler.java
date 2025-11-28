package dev.nk7.bot.notifier.handler;

import dev.nk7.bot.notifier.api.SendNotificationRequest;
import dev.nk7.bot.notifier.core.port.in.SendNotificationUseCase;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

public class SendNotificationHandler extends UseCaseHandler<SendNotificationUseCase> {

  public SendNotificationHandler(SendNotificationUseCase useCase) {
    super(useCase);
  }

  @Override
  public void handle(@NotNull Context ctx) {
    final SendNotificationRequest request = ctx.bodyStreamAsClass(SendNotificationRequest.class);
    useCase().sendNotification(request.text(), request.tags());
  }
}

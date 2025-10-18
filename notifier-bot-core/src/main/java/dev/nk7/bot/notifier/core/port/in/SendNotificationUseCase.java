package dev.nk7.bot.notifier.core.port.in;

import dev.nk7.bot.notifier.core.model.Notification;

public interface SendNotificationUseCase {

  void sendNotification(Notification notification);

}

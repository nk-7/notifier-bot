package dev.nk7.bot.notifier.core.port.out;

import dev.nk7.bot.notifier.core.model.Chat;
import dev.nk7.bot.notifier.core.model.Notification;

public interface NotificationSender {
  void sendNotification(Chat chat, Notification notification);
}

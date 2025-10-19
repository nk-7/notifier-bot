package dev.nk7.bot.notifier.core.port.in;

import java.util.Set;

public interface SendNotificationUseCase {

  void sendNotification(String text, Set<String> tags);
}

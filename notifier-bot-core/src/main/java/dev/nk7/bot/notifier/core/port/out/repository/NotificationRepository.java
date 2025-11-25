package dev.nk7.bot.notifier.core.port.out.repository;

import dev.nk7.bot.notifier.core.model.Notification;

public interface NotificationRepository {

  void save(Notification notification);
}

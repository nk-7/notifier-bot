package dev.nk7.bot.notifier.model;

public enum ChatStatus {
  /**
   * Новый чат, отправка уведомлений в него запрещена.
   */
  NEW,
  /**
   * Известный, одобренный чат, отправка уведомлений разрешена.
   */
  APPROVED
}

package dev.nk7.bot.notifier.core.model;

/**
 * Статус чата.
 */
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

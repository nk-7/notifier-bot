package dev.nk7.bot.notifier.telegram;

public class TelegramException extends RuntimeException {

  public TelegramException(String message, Throwable cause) {
    super(message, cause);
  }
}

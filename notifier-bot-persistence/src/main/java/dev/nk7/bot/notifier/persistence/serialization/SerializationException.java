package dev.nk7.bot.notifier.persistence.serialization;

public class SerializationException extends RuntimeException {

  public SerializationException(String message, Throwable cause) {
    super(message, cause);
  }

  public SerializationException(Throwable cause) {
    super(cause);
  }
}

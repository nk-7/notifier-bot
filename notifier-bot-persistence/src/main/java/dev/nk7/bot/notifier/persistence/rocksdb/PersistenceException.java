package dev.nk7.bot.notifier.persistence.rocksdb;

public class PersistenceException extends RuntimeException {


  public PersistenceException(String message, Throwable cause) {
    super(message, cause);
  }
}

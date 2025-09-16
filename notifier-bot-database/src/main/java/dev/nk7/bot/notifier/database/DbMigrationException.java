package dev.nk7.bot.notifier.database;

public class DbMigrationException extends RuntimeException {
  public DbMigrationException(String message, Throwable cause) {
    super(message, cause);
  }
}

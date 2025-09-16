package dev.nk7.bot.notifier.database;

public class Application {
  public static final String CHANGE_LOG = "db.changelog/db.changelog-master.yaml";

  public static void main(String[] args) {
    new Migrator(CHANGE_LOG,
      System.getenv("LIQUIBASE_URL"),
      System.getenv("LIQUIBASE_USERNAME"),
      System.getenv("LIQUIBASE_PASSWORD")
    ).migrate();
  }
}

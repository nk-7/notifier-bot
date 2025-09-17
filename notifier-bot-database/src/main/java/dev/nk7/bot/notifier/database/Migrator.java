package dev.nk7.bot.notifier.database;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public record Migrator(String changelog, String jdbcUrl, String username, String password) {
  public void migrate() throws DbMigrationException {
    try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
      final DatabaseFactory databaseFactory = DatabaseFactory.getInstance();
      final Database correctDatabaseImplementation = databaseFactory.findCorrectDatabaseImplementation(new JdbcConnection(connection));
      try (Liquibase liquibase = new Liquibase(changelog, new ClassLoaderResourceAccessor(), correctDatabaseImplementation);) {
        liquibase.update();
      }
    } catch (SQLException | LiquibaseException e) {
      throw new DbMigrationException(e.getMessage(), e);
    }
  }
}

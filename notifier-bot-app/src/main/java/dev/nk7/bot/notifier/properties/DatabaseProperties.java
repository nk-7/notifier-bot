package dev.nk7.bot.notifier.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.database")
public record DatabaseProperties(
  String driver,
  String protocol,
  String host,
  Integer port,
  String database,
  String username,
  String password
) {

}

package dev.nk7.bot.notifier.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.rocksdb")
public record RocksDbProperties(String path) {
}

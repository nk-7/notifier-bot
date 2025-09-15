package dev.nk7.bot.notifier.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.bot")
public record TelegramBotProperties(String token) {
}

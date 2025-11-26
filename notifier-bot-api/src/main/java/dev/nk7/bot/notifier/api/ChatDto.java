package dev.nk7.bot.notifier.api;

import java.util.Set;

public record ChatDto(
  Long chatId,
  String title,
  String type,
  String status,
  Set<String> subscriptions
) {
}


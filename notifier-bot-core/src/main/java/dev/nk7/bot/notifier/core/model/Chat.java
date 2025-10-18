package dev.nk7.bot.notifier.core.model;


import java.util.Set;

public record Chat(
  Long chatId,
  String title,
  String type,
  ChatStatus status,
  Set<Tag> subscriptions
) {
}

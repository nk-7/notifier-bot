package dev.nk7.bot.notifier.model;


public record Chat(
  Long id,
  Long chatId,
  String title,
  String type,
  ChatStatus status
) {
}

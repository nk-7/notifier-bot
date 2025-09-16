package dev.nk7.bot.notifier.model;


public record Chat(
  Long id,
  Long chatId,
  ChatStatus status
) {
}

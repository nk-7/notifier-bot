package dev.nk7.bot.notifier.persistence.table;


public record ChatRow(
  Long id,
  Long chatId,
  String title,
  String type,
  String status
) {
}

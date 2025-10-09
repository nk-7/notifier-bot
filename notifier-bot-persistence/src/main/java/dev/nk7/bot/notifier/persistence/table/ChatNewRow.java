package dev.nk7.bot.notifier.persistence.table;

public record ChatNewRow(Long chatId,
                         String title,
                         String type,
                         String status) {
}

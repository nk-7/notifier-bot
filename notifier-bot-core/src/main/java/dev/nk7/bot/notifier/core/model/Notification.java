package dev.nk7.bot.notifier.core.model;

import java.util.Set;

public record Notification(Long id, String text, Set<String> tags, NotificationStatus status) {
}

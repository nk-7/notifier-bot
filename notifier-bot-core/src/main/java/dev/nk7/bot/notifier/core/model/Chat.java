package dev.nk7.bot.notifier.core.model;


import java.util.Objects;
import java.util.Set;

public record Chat(
  Long chatId,
  String title,
  String type,
  ChatStatus status,
  Set<String> subscriptions
) {

  public boolean subscribed(Set<String> tags) {
    if (subscriptions.isEmpty() || tags.isEmpty()) {
      return false;
    }
    for (final String tag : tags) {
      if (subscriptions.contains(tag)) {
        return true;
      }
    }
    return false;
  }

  public boolean isApproved() {
    return status == ChatStatus.APPROVED;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    final Chat chat = (Chat) o;
    return Objects.equals(chatId, chat.chatId);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(chatId);
  }
}

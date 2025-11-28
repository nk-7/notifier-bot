package dev.nk7.bot.notifier.core.model;


import java.beans.Transient;
import java.util.Objects;
import java.util.Set;

public record Chat(
  Long chatId,
  String title,
  String type,
  ChatStatus status,
  Set<String> subscriptions
) {

  @Transient
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

  @Transient
  public boolean isApproved() {
    return status == ChatStatus.APPROVED;
  }


  public Chat withStatus(ChatStatus newStatus) {
    return new Chat(chatId, title, type, newStatus, subscriptions);
  }

  public Chat withSubscriptions(Set<String> subscriptions) {
    return new Chat(chatId, title, type, status, subscriptions);
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

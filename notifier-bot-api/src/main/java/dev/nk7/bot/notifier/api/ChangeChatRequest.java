package dev.nk7.bot.notifier.api;

import java.util.Set;

public record ChangeChatRequest(String status, Set<String> subscriptions) {
}

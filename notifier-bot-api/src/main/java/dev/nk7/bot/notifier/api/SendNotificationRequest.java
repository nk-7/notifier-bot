package dev.nk7.bot.notifier.api;

import java.util.Set;

public record SendNotificationRequest(String text, Set<String> tags) {

}

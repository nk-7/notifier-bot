package dev.nk7.bot.notifier.core.port.out.service;

public interface MessageService {
  void send(String chatId, String message);
}

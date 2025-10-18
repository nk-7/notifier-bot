package dev.nk7.bot.notifier.core.port.out;

public interface MessageService {
  void send(String chatId, String message);
}

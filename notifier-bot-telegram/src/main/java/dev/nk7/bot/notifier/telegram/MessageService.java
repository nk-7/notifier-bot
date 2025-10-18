package dev.nk7.bot.notifier.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.Objects;

public class MessageService implements dev.nk7.bot.notifier.core.port.out.MessageService {

  private final TelegramClient telegramClient;

  public MessageService(TelegramClient telegramClient) {
    this.telegramClient = Objects.requireNonNull(telegramClient);
  }

  @Override
  public void send(String chatId, String message) throws TelegramException {
    try {
      final SendMessage sendMessage = new SendMessage(chatId, message);
      telegramClient.execute(sendMessage);
    } catch (TelegramApiException e) {
      throw new TelegramException(e.getMessage(), e);
    }
  }
}

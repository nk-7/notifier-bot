package dev.nk7.bot.notifier.modules;

import dev.nk7.bot.notifier.ApplicationProperties;
import dev.nk7.bot.notifier.core.port.out.service.MessageService;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;

class ServicesModuleImpl implements ServicesModule {

  private final TelegramClient telegramClient;

  ServicesModuleImpl(ApplicationProperties applicationProperties) {
    this.telegramClient = telegramClient(applicationProperties.getTelegramToken());
  }

  @Override
  public MessageService messageService() {
    return new dev.nk7.bot.notifier.telegram.service.MessageService(telegramClient);
  }

  private TelegramClient telegramClient(String token) {
    return new OkHttpTelegramClient(token);
  }


}

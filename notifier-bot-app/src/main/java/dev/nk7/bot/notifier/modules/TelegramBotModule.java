package dev.nk7.bot.notifier.modules;

import dev.nk7.bot.notifier.ApplicationProperties;
import dev.nk7.bot.notifier.telegram.TelegramBot;

public interface TelegramBotModule {

  TelegramBot telegramBot();


  static TelegramBotModule of(ApplicationProperties applicationProperties, UseCasesModule useCasesModule) {
    return new TelegramBotModuleImpl(applicationProperties, useCasesModule);
  }

}

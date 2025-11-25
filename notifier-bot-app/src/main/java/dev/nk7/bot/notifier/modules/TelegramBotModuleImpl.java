package dev.nk7.bot.notifier.modules;

import dev.nk7.bot.notifier.ApplicationProperties;
import dev.nk7.bot.notifier.StartCommandHandler;
import dev.nk7.bot.notifier.telegram.TelegramBot;
import dev.nk7.bot.notifier.telegram.handler.CommandHandler;

import java.util.Objects;

class TelegramBotModuleImpl implements TelegramBotModule {

  private final TelegramBot telegramBot;

  TelegramBotModuleImpl(ApplicationProperties applicationProperties, UseCasesModule useCasesModule) {
    Objects.requireNonNull(useCasesModule);
    this.telegramBot = telegramBot(applicationProperties.getTelegramToken(), useCasesModule);
  }

  @Override
  public TelegramBot telegramBot() {
    return telegramBot;
  }

  private TelegramBot telegramBot(String token, UseCasesModule useCasesModule) {
    return new TelegramBot(token, startCommandHandler(useCasesModule));
  }

  private CommandHandler startCommandHandler(UseCasesModule useCasesModule) {
    return new StartCommandHandler(useCasesModule.addNewChatUseCase());
  }
}

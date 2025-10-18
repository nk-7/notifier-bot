package dev.nk7.bot.notifier.telegram;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

public abstract class CommandHandler extends UpdateHandler {


  private final String command;

  protected CommandHandler(String command) {
    this.command = Objects.requireNonNull(command);
  }

  @Override
  protected boolean filter(Update update) {
    return update.hasMessage() && update.getMessage().isCommand() && update.getMessage().getText().startsWith(command);
  }


}

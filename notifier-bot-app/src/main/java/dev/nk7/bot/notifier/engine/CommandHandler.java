package dev.nk7.bot.notifier.engine;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

public abstract class CommandHandler extends UpdateHandler {


  private final String command;

  protected CommandHandler(String command) {
    this.command = Objects.requireNonNull(command);
  }

  @Override
  protected boolean filter(UpdateEvent event) {
    final Update update = event.getUpdate();
    return update.hasMessage() && update.getMessage().isCommand() && update.getMessage().getText().startsWith(command);
  }


}

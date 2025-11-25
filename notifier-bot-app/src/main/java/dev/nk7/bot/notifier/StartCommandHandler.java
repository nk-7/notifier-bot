package dev.nk7.bot.notifier;


import dev.nk7.bot.notifier.core.port.in.AddNewChatUseCase;
import dev.nk7.bot.notifier.telegram.handler.CommandHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

public class StartCommandHandler extends CommandHandler {

  private final AddNewChatUseCase addNewChatUseCase;

  public StartCommandHandler(AddNewChatUseCase addNewChatUseCase) {
    super("/start");
    this.addNewChatUseCase = Objects.requireNonNull(addNewChatUseCase);
  }

  @Override
  protected void handle(Update update) {
    final Long chatId = update.getMessage().getChatId();
    final String title = update.getMessage().getChat().getTitle();
    final String type = update.getMessage().getChat().getType();
    addNewChatUseCase.addNewChat(chatId, title, type);
  }
}

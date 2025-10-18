package dev.nk7.bot.notifier.handler;


import dev.nk7.bot.notifier.core.port.in.AddNewChatUseCase;
import dev.nk7.bot.notifier.engine.CommandHandler;
import dev.nk7.bot.notifier.engine.UpdateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

@Component
public class StartCommandHandler extends CommandHandler {
  private static final Logger log = LoggerFactory.getLogger(StartCommandHandler.class);


  private final AddNewChatUseCase addNewChatUseCase;

  protected StartCommandHandler(AddNewChatUseCase addNewChatUseCase) {
    super("/start");
    this.addNewChatUseCase = Objects.requireNonNull(addNewChatUseCase);
  }

  @Override
  protected void handle(UpdateEvent event) {
    final Update update = event.getUpdate();
    final Long chatId = update.getMessage().getChatId();
    final String title = update.getMessage().getChat().getTitle();
    final String type = update.getMessage().getChat().getType();
    addNewChatUseCase.addNewChat(chatId, title, type);
  }
}

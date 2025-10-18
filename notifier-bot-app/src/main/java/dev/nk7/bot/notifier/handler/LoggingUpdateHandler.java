package dev.nk7.bot.notifier.handler;


import dev.nk7.bot.notifier.engine.UpdateEvent;
import dev.nk7.bot.notifier.engine.UpdateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class LoggingUpdateHandler extends UpdateHandler {
  private static final Logger log = LoggerFactory.getLogger(LoggingUpdateHandler.class);


  @Override
  protected boolean filter(UpdateEvent event) {
    return true;
  }

  @Override
  protected void handle(UpdateEvent event) {
    log.trace("Получено обновление {}", event);
  }
}

package dev.nk7.bot.notifier.telegram;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import dev.nk7.bot.notifier.telegram.handler.UpdateEvent;
import dev.nk7.bot.notifier.telegram.handler.UpdateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot {
  private static final Logger log = LoggerFactory.getLogger(TelegramBot.class);
  private final TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
  private final Disruptor<UpdateEvent> disruptor = new Disruptor<>(UpdateEvent::new,
    64,
    DaemonThreadFactory.INSTANCE,
    ProducerType.SINGLE,
    new BlockingWaitStrategy()
  );


  public TelegramBot(String token, UpdateHandler... handlers) {
    try {
      botsApplication.registerBot(token, new SendDisruptorUpdateConsumer(disruptor));
      disruptor.handleEventsWith(handlers);
    } catch (TelegramApiException e) {
      throw new TelegramException("Error register bot", e);
    }
  }


  public void start() {
    log.info("Starting TelegramBot");
    disruptor.start();
    try {
      if (!botsApplication.isRunning()) {
        botsApplication.start();
      }
    } catch (TelegramApiException e) {
      throw new TelegramException("Error starting telegram bot", e);
    }
    Runtime.getRuntime().addShutdownHook(new Thread(this::close));
  }

  public void close() {
    log.debug("Shutting down TelegramBot.");
    try {
      if (botsApplication.isRunning()) {
        botsApplication.close();
      }
      disruptor.shutdown();
    } catch (Exception e) {
      throw new TelegramException("Error shutting down telegram bot", e);
    }
  }
}

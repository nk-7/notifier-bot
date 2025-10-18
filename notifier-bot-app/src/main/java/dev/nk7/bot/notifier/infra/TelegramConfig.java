package dev.nk7.bot.notifier.infra;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import dev.nk7.bot.notifier.infra.properties.TelegramBotProperties;
import dev.nk7.bot.notifier.telegram.MessageService;
import dev.nk7.bot.notifier.telegram.UpdateEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Configuration
@EnableConfigurationProperties({TelegramBotProperties.class})
public class TelegramConfig {

  @Bean(destroyMethod = "close")
  TelegramBotsLongPollingApplication telegramBotsLongPollingApplication(TelegramBotProperties telegramBotProperties, LongPollingSingleThreadUpdateConsumer consumer) throws TelegramApiException {
    final TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
    botsApplication.registerBot(telegramBotProperties.token(), consumer);
    return botsApplication;
  }

  @Bean
  TelegramClient telegramClient(TelegramBotProperties telegramBotProperties) {
    return new OkHttpTelegramClient(telegramBotProperties.token());
  }

  @Bean
  LongPollingSingleThreadUpdateConsumer updateConsumer(Disruptor<UpdateEvent> disruptor) {
    return update -> {
      final RingBuffer<UpdateEvent> ringBuffer = disruptor.getRingBuffer();
      ringBuffer.publishEvent((event, sequence) -> {
        event.setUpdate(update);
      });
    };
  }

  @Bean
  MessageService messageService(TelegramClient telegramClient) {
    return new dev.nk7.bot.notifier.telegram.MessageService(telegramClient);
  }

}

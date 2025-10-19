package dev.nk7.bot.notifier.notify;

import dev.nk7.bot.notifier.core.port.in.SendNotificationUseCase;
import dev.nk7.bot.notifier.core.port.out.ChatRepository;
import dev.nk7.bot.notifier.core.port.out.MessageService;
import dev.nk7.bot.notifier.core.port.out.NotificationRepository;
import dev.nk7.bot.notifier.persistence.rocksdb.Entities;
import dev.nk7.bot.notifier.persistence.rocksdb.Rocks;
import dev.nk7.bot.notifier.persistence.RocksNotificationRepository;
import dev.nk7.bot.notifier.persistence.serialization.Serializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendNotificationUseCaseConfig {

  @Bean
  SendNotificationUseCase sendNotificationUseCase(MessageService messageService, ChatRepository chatRepository) {
    return new dev.nk7.bot.notifier.core.usecase.SendNotificationUseCase(messageService, chatRepository);
  }

  @Bean
  NotificationRepository notificationRepository(Rocks<Entities> rocks, Serializer serializer) {
    return new RocksNotificationRepository(rocks, serializer);
  }
}

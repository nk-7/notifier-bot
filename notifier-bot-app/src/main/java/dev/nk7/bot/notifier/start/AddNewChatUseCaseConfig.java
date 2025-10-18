package dev.nk7.bot.notifier.start;

import dev.nk7.bot.notifier.core.port.in.AddNewChatUseCase;
import dev.nk7.bot.notifier.core.port.out.ChatRepository;
import dev.nk7.bot.notifier.core.port.out.MessageService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ConfigurationProperties
public class AddNewChatUseCaseConfig {

  @Bean
  AddNewChatUseCase addNewChatUseCase(ChatRepository chatRepository, MessageService messageService) {
    return new dev.nk7.bot.notifier.core.usecase.AddNewChatUseCase(chatRepository, messageService);
  }
}

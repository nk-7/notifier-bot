package dev.nk7.bot.notifier;


import dev.nk7.bot.notifier.config.AddNewChatUseCaseConfig;
import dev.nk7.bot.notifier.config.DatabaseConfig;
import dev.nk7.bot.notifier.config.DisruptorConfig;
import dev.nk7.bot.notifier.config.TelegramConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication

@Import({DisruptorConfig.class, DatabaseConfig.class, AddNewChatUseCaseConfig.class, TelegramConfig.class,})
public class Application {
  private static final Logger log = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}


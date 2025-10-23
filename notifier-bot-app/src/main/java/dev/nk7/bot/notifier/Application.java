package dev.nk7.bot.notifier;


import dev.nk7.bot.notifier.infra.DatabaseConfig;
import dev.nk7.bot.notifier.infra.DisruptorConfig;
import dev.nk7.bot.notifier.infra.RestConfig;
import dev.nk7.bot.notifier.infra.TelegramConfig;
import dev.nk7.bot.notifier.start.AddNewChatUseCaseConfig;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication

@Import({DisruptorConfig.class, DatabaseConfig.class, AddNewChatUseCaseConfig.class, TelegramConfig.class, RestConfig.class})
public class Application implements ApplicationRunner {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }


  @Override
  public void run(ApplicationArguments args) throws Exception {

  }
}


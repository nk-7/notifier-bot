package dev.nk7.bot.notifier;


import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import dev.nk7.bot.notifier.core.ActorFactoryBean;
import dev.nk7.bot.notifier.properties.TelegramBotProperties;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
@EnableConfigurationProperties(TelegramBotProperties.class)
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean(destroyMethod = "close")
  TelegramBotsLongPollingApplication telegramBotsLongPollingApplication(TelegramBotProperties telegramBotProperties, LongPollingSingleThreadUpdateConsumer consumer) throws TelegramApiException {
    final TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
    botsApplication.registerBot(telegramBotProperties.token(), consumer);
    return botsApplication;
  }


  @Bean
  ActorSystem<RootActor.Api> actorSystem() {
    return ActorSystem.create(RootActor.create(), "actor-system");
  }

  @Bean
  FactoryBean<ActorRef<Update>> updatesRouterActor(ActorSystem<RootActor.Api> actorSystem) {
    return new ActorFactoryBean<>(actorSystem,
      UpdatesRouterActor.create(),
      "update-router");
  }

  @Bean
  LongPollingSingleThreadUpdateConsumer updateConsumer(ActorRef<Update> updatesRouterActor) {
    return updatesRouterActor::tell;
  }
}


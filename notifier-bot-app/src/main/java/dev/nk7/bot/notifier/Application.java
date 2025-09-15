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
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

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
  TelegramClient telegramClient(TelegramBotProperties telegramBotProperties) {
    return new OkHttpTelegramClient(telegramBotProperties.token());
  }


  @Bean
  ActorSystem<RootActor.Api> actorSystem() {
    return ActorSystem.create(RootActor.create(), "actor-system");
  }

  @Bean
  FactoryBean<ActorRef<BotApiMethod<?>>> telegramClientActor(ActorSystem<RootActor.Api> actorSystem, TelegramClient telegramClient) {
    return new ActorFactoryBean<>(
      actorSystem,
      TelegramClientActor.create(telegramClient),
      "telegram-client-actor"
    );
  }

  @Bean
  FactoryBean<ActorRef<Update>> updatesRouterActor(ActorSystem<RootActor.Api> actorSystem, ActorRef<BotApiMethod<?>> telegramClient) {
    return new ActorFactoryBean<>(actorSystem,
      UpdatesRouterActor.create(telegramClient),
      "update-router");
  }

  @Bean
  LongPollingSingleThreadUpdateConsumer updateConsumer(ActorRef<Update> updatesRouterActor) {
    return updatesRouterActor::tell;
  }
}


package dev.nk7.bot.notifier;


import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.SpawnProtocol;
import dev.nk7.bot.notifier.actor.TelegramClientActor;
import dev.nk7.bot.notifier.actor.UpdatesRouterActor;
import dev.nk7.bot.notifier.core.ActorFactoryBean;
import dev.nk7.bot.notifier.persistence.repository.ChatRepository;
import dev.nk7.bot.notifier.persistence.repository.r2dbc.R2dbcChatRepository;
import dev.nk7.bot.notifier.properties.DatabaseProperties;
import dev.nk7.bot.notifier.properties.TelegramBotProperties;
import dev.nk7.bot.notifier.service.ChatService;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@EnableConfigurationProperties({TelegramBotProperties.class, DatabaseProperties.class})
public class Application {
  private static final Logger log = LoggerFactory.getLogger(Application.class);

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
  ActorSystem<SpawnProtocol.Command> actorSystem() {
    return ActorSystem.create(SpawnProtocol.create(), "actor-system");
  }

  @Bean
  FactoryBean<ActorRef<BotApiMethod<?>>> telegramClientActor(ActorSystem<SpawnProtocol.Command> actorSystem, TelegramClient telegramClient) {
    return new ActorFactoryBean<>(
      actorSystem,
      TelegramClientActor.create(telegramClient),
      "telegram-client-actor"
    );
  }

  @Bean
  FactoryBean<ActorRef<Update>> updatesRouterActor(ActorSystem<SpawnProtocol.Command> actorSystem,
                                                   ActorRef<BotApiMethod<?>> telegramClient,
                                                   ChatService chatService) {
    return new ActorFactoryBean<>(actorSystem,
      UpdatesRouterActor.create(telegramClient, chatService),
      "update-router");
  }

  @Bean
  LongPollingSingleThreadUpdateConsumer updateConsumer(ActorRef<Update> updatesRouterActor) {
    return updatesRouterActor::tell;
  }

  @Bean
  ConnectionFactory connectionFactory(DatabaseProperties databaseProperties) {
    final ConnectionFactoryOptions.Builder options = ConnectionFactoryOptions.builder()
      .option(ConnectionFactoryOptions.DRIVER, databaseProperties.driver())
      .option(ConnectionFactoryOptions.PROTOCOL, databaseProperties.protocol())
      .option(ConnectionFactoryOptions.HOST, databaseProperties.host())
      .option(ConnectionFactoryOptions.DATABASE, databaseProperties.database());
    if (databaseProperties.port() != null) {
      options.option(ConnectionFactoryOptions.PORT, databaseProperties.port());
    }


    if (databaseProperties.username() != null) {
      options.option(ConnectionFactoryOptions.USER, databaseProperties.username());
    }
    if (databaseProperties.password() != null) {
      options.option(ConnectionFactoryOptions.PASSWORD, databaseProperties.password());
    }
    return ConnectionFactories.find(options.build());
  }

  @Bean
  ChatRepository chatRepository(ConnectionFactory connectionFactory) {
    return new R2dbcChatRepository(connectionFactory);
  }

}


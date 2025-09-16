package dev.nk7.bot.notifier.actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

public class StartCommandHandlerActor extends AbstractBehavior<StartCommandHandlerActor.Api> {


  public sealed interface Api {
    record StartCommand(Long chatId) implements Api {
      public static StartCommand fromUpdate(Update update) {
        return new StartCommand(update.getMessage().getChatId());
      }
    }
  }

  private final ActorRef<BotApiMethod<?>> telegramClient;

  public StartCommandHandlerActor(ActorContext<StartCommandHandlerActor.Api> context, ActorRef<BotApiMethod<?>> telegramClient) {
    super(context);
    this.telegramClient = Objects.requireNonNull(telegramClient);
  }

  public static Behavior<StartCommandHandlerActor.Api> create(ActorRef<BotApiMethod<?>> telegramClient) {
    return Behaviors.setup(ctx -> new StartCommandHandlerActor(ctx, telegramClient));
  }

  @Override
  public Receive<StartCommandHandlerActor.Api> createReceive() {
    return newReceiveBuilder()
      .onMessage(StartCommandHandlerActor.Api.StartCommand.class, this::handleStart)
      .build();
  }

  private Behavior<StartCommandHandlerActor.Api> handleStart(StartCommandHandlerActor.Api.StartCommand startCommand) {
    getContext().getLog().info("Вызвана команда старт в чате '{}'", startCommand.chatId());
    telegramClient.tell(new SendMessage(startCommand.chatId().toString(), "Привет!"));
    return Behaviors.stopped();
  }
}

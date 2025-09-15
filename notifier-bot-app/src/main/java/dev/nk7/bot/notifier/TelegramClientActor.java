package dev.nk7.bot.notifier;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.Objects;

public class TelegramClientActor extends AbstractBehavior<BotApiMethod<?>> {
  private final TelegramClient telegramClient;


  private TelegramClientActor(ActorContext<BotApiMethod<?>> context, TelegramClient telegramClient) {
    super(context);
    this.telegramClient = Objects.requireNonNull(telegramClient);
  }


  public static Behavior<BotApiMethod<?>> create(TelegramClient telegramClient) {
    return Behaviors.setup(ctx -> new TelegramClientActor(ctx, telegramClient));
  }


  @Override
  public Receive<BotApiMethod<?>> createReceive() {
    return newReceiveBuilder()
      .onMessage(BotApiMethod.class, this::execute)
      .build();
  }

  private Behavior<BotApiMethod<?>> execute(BotApiMethod<?> botApiMethod) throws TelegramApiException {
    telegramClient.execute(botApiMethod);
    return Behaviors.same();
  }
}

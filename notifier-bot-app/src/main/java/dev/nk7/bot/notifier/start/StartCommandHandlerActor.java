package dev.nk7.bot.notifier.start;

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

public class StartCommandHandlerActor extends AbstractBehavior<Update> {

  private final ActorRef<BotApiMethod<?>> telegramClient;

  public StartCommandHandlerActor(ActorContext<Update> context, ActorRef<BotApiMethod<?>> telegramClient) {
    super(context);
    this.telegramClient = Objects.requireNonNull(telegramClient);
  }

  public static Behavior<Update> create(ActorRef<BotApiMethod<?>> telegramClient) {
    return Behaviors.setup(ctx -> new StartCommandHandlerActor(ctx, telegramClient));
  }

  @Override
  public Receive<Update> createReceive() {
    return newReceiveBuilder()
      .onMessage(Update.class, this::handleStart)
      .build();
  }

  private Behavior<Update> handleStart(Update update) {
    final Long chatId = update.getMessage().getChatId();
    getContext().getLog().info("Вызвана команда старт в чате '{}'", chatId);
    telegramClient.tell(new SendMessage(chatId.toString(), "Привет!"));
    return Behaviors.stopped();
  }
}

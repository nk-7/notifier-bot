package dev.nk7.bot.notifier.actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.LogOptions;
import akka.actor.typed.SupervisorStrategy;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import org.slf4j.event.Level;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

public class UpdatesRouterActor extends AbstractBehavior<Update> {
  private final ActorRef<BotApiMethod<?>> telegramClient;

  private UpdatesRouterActor(ActorContext<Update> context, ActorRef<BotApiMethod<?>> telegramClient) {
    super(context);
    this.telegramClient = Objects.requireNonNull(telegramClient);
  }

  public static Behavior<Update> create(ActorRef<BotApiMethod<?>> telegramClient) {

    final Behavior<Update> behaviour = Behaviors.logMessages(LogOptions.create().withLevel(Level.DEBUG),
      Behaviors.setup(ctx -> new UpdatesRouterActor(ctx, telegramClient)));
    return Behaviors.supervise(behaviour).onFailure(SupervisorStrategy.restart());

  }

  @Override
  public Receive<Update> createReceive() {
    return newReceiveBuilder()
      .onMessage(Update.class, this::onUpdate)
      .build();
  }

  private Behavior<Update> onUpdate(Update update) {
    if (update.hasMessage() && update.getMessage().isCommand()) {
      routeCommand(update);
    }
    return Behaviors.same();

  }

  private void routeCommand(Update update) {
    if (Objects.equals(update.getMessage().getText(), "/start")) {
      final ActorRef<StartCommandHandlerActor.Api> startHandler = getContext().spawn(StartCommandHandlerActor.create(telegramClient), "start-command-handler-" + update.getMessage().getChatId());
      startHandler.tell(StartCommandHandlerActor.Api.StartCommand.fromUpdate(update));
    }
  }
}

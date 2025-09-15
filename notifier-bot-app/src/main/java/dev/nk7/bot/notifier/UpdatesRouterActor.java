package dev.nk7.bot.notifier;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UpdatesRouterActor extends AbstractBehavior<Update> {

  private UpdatesRouterActor(ActorContext<Update> context) {
    super(context);
  }

  public static Behavior<Update> create() {
    return Behaviors.setup(UpdatesRouterActor::new);
  }

  @Override
  public Receive<Update> createReceive() {
    return newReceiveBuilder()
      .onMessage(Update.class, this::onUpdate)
      .build();
  }

  private Behavior<Update> onUpdate(Update update) {
    getContext().getLog().info("Получено обновление {}.", update);
    return Behaviors.same();

  }
}

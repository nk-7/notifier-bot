package dev.nk7.bot.notifier;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class RootActor extends AbstractBehavior<RootActor.Api> {

  private RootActor(ActorContext<Api> context) {
    super(context);
  }

  public static Behavior<Api> create() {
    return Behaviors.setup(RootActor::new);
  }

  public sealed interface Api {
    record CreateActor<T>(Behavior<T> behaviour, String name, ActorRef<ActorCreated<T>> replyTo) implements Api {
    }

    record ActorCreated<T>(ActorRef<T> actor) implements Api {

    }
  }

  @Override
  public Receive<Api> createReceive() {
    return newReceiveBuilder()
      .onMessage(Api.CreateActor.class, this::createActor).build();
  }

  private <T> Behavior<Api> createActor(Api.CreateActor<T> createActor) {
    final ActorRef<T> actor = getContext().spawn(createActor.behaviour, createActor.name);
    createActor.replyTo.tell(new Api.ActorCreated<>(actor));
    return Behaviors.same();

  }


}

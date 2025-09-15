package dev.nk7.bot.notifier.core;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AskPattern;
import dev.nk7.bot.notifier.RootActor;
import org.springframework.beans.factory.FactoryBean;

import java.time.Duration;

public record ActorFactoryBean<T>(ActorSystem<RootActor.Api> actorSystem,
                                  Behavior<T> behavior,
                                  String name)
  implements FactoryBean<ActorRef<T>> {


  @Override
  public ActorRef<T> getObject() throws Exception {
    return AskPattern.ask(
      actorSystem,
      (ActorRef<RootActor.Api.ActorCreated<T>> replyTo) -> new RootActor.Api.CreateActor<>(behavior, name, replyTo.narrow()),
      Duration.ofSeconds(5),
      null
    ).toCompletableFuture().get().actor();
  }

  @Override
  public Class<?> getObjectType() {
    return ActorRef.class;
  }
}

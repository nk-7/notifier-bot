package dev.nk7.bot.notifier.core;

import akka.actor.typed.*;
import akka.actor.typed.javadsl.AskPattern;
import org.springframework.beans.factory.FactoryBean;

import java.time.Duration;

public record ActorFactoryBean<T>(ActorSystem<SpawnProtocol.Command> actorSystem,
                                  Behavior<T> behavior,
                                  String name)
  implements FactoryBean<ActorRef<T>> {


  @Override
  public ActorRef<T> getObject() throws Exception {
    return AskPattern.ask(
      actorSystem,
      (ActorRef<ActorRef<T>> replyTo) -> new SpawnProtocol.Spawn<>(behavior.narrow(), name, Props.empty(), replyTo.narrow()),
      Duration.ofSeconds(5),
      null
    ).toCompletableFuture().get();
  }

  @Override
  public Class<?> getObjectType() {
    return ActorRef.class;
  }
}

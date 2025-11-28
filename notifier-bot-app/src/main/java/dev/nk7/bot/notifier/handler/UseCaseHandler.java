package dev.nk7.bot.notifier.handler;

import io.javalin.http.Handler;

import java.util.Objects;

abstract class UseCaseHandler<U> implements Handler {

  private final U useCase;

  public UseCaseHandler(U useCase) {
    this.useCase = Objects.requireNonNull(useCase);
  }

  protected U useCase() {
    return useCase;
  }

}

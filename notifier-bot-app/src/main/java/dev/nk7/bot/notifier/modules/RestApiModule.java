package dev.nk7.bot.notifier.modules;

import io.javalin.Javalin;

public interface RestApiModule {

  Javalin javalin();


  static RestApiModule of(UseCasesModule useCasesModule) {
    return new RestApiModuleImpl(useCasesModule);
  }

}

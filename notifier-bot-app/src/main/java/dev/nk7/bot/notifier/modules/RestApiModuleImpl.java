package dev.nk7.bot.notifier.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dev.nk7.bot.notifier.handler.ChangeChatHandler;
import dev.nk7.bot.notifier.handler.GetChatsHandler;
import dev.nk7.bot.notifier.handler.SendNotificationHandler;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.json.JavalinJackson;
import io.javalin.json.JsonMapper;

import java.util.Objects;

class RestApiModuleImpl implements RestApiModule {

  private final UseCasesModule useCasesModule;

  RestApiModuleImpl(UseCasesModule useCasesModule) {
    this.useCasesModule = Objects.requireNonNull(useCasesModule);
  }

  @Override
  public Javalin javalin() {
    return Javalin.create(cfg -> cfg.jsonMapper(jsonMapper()))
      .post("/api/v1/notification", sendNotificationHandler())
      .get("/console/chats", getChatsHandler())
      .patch("/console/chat/{chatId}", changeChatStatusHandler());
  }


  private JsonMapper jsonMapper() {
    return new JavalinJackson(
      new ObjectMapper()
        .enable(SerializationFeature.INDENT_OUTPUT),
      false
    );
  }


  private Handler getChatsHandler() {
    return new GetChatsHandler(useCasesModule.getChatsUseCase());
  }

  private Handler sendNotificationHandler() {
    return new SendNotificationHandler(useCasesModule.sendNotificationUseCase());
  }

  private Handler changeChatStatusHandler() {
    return new ChangeChatHandler(useCasesModule.changeChatStatusUseCase());
  }


}

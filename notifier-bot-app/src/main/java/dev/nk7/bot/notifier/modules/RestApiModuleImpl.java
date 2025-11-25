package dev.nk7.bot.notifier.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dev.nk7.bot.notifier.api.SendNotificationRequest;
import io.javalin.Javalin;
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
      .post("/api/v1/notification", ctx -> {
        final SendNotificationRequest request = ctx.bodyStreamAsClass(SendNotificationRequest.class);
        useCasesModule.sendNotificationUseCase().sendNotification(request.text(), request.tags());
      })
      .get("/console/chats", ctx -> {
        ctx.contentType("application/json");
        ctx.json(useCasesModule.getChatsUseCase().getChats());
      });
  }


  private JsonMapper jsonMapper() {
    return new JavalinJackson(
      new ObjectMapper()
        .enable(SerializationFeature.INDENT_OUTPUT),
      false
    );
  }


}

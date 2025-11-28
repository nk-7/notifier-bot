package dev.nk7.bot.notifier.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dev.nk7.bot.notifier.api.ChangeChatStatusRequest;
import dev.nk7.bot.notifier.api.ChangeChatStatusResponse;
import dev.nk7.bot.notifier.api.ChatDto;
import dev.nk7.bot.notifier.api.SendNotificationRequest;
import dev.nk7.bot.notifier.core.model.Chat;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import io.javalin.json.JsonMapper;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
        final Set<Chat> chats = useCasesModule.getChatsUseCase().getChats();
        final Set<ChatDto> chatDtoSet = chats.stream()
          .map(c -> new ChatDto(
            c.chatId(),
            c.title(),
            c.type(),
            c.status().name(),
            c.subscriptions())
          ).collect(Collectors.toSet());
        ctx.contentType("application/json");
        ctx.json(chatDtoSet);
      })
      .patch("/console/chat/{chatId}", ctx -> {
        final Long chatId = ctx.pathParamAsClass("chatId", Long.class).get();
        final ChangeChatStatusRequest changeChatStatusRequest = ctx.bodyAsClass(ChangeChatStatusRequest.class);
        final Chat changedChat = useCasesModule.changeChatStatusUseCase().change(chatId, changeChatStatusRequest.status());
        final ChangeChatStatusResponse response = new ChangeChatStatusResponse(
          chatId, changedChat.title(), changedChat.type(), changedChat.status().name(), changedChat.subscriptions()
        );
        ctx.contentType("application/json");
        ctx.json(response);
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

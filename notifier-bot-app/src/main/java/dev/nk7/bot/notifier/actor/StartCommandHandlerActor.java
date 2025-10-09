package dev.nk7.bot.notifier.actor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import dev.nk7.bot.notifier.service.ChatService;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;

import java.util.Objects;

public class StartCommandHandlerActor extends AbstractBehavior<StartCommandHandlerActor.Api> {


  public sealed interface Api {
    record StartCommand(Chat chat) implements Api {
      public static StartCommand fromUpdate(Update update) {
        return new StartCommand(update.getMessage().getChat());
      }
    }
  }

  private final ActorRef<BotApiMethod<?>> telegramClient;
  private final ChatService chatService;

  public StartCommandHandlerActor(ActorContext<StartCommandHandlerActor.Api> context, ActorRef<BotApiMethod<?>> telegramClient, ChatService chatService) {
    super(context);
    this.telegramClient = Objects.requireNonNull(telegramClient);
    this.chatService = Objects.requireNonNull(chatService);
  }

  public static Behavior<StartCommandHandlerActor.Api> create(ActorRef<BotApiMethod<?>> telegramClient, ChatService chatService) {
    return Behaviors.setup(ctx -> new StartCommandHandlerActor(ctx, telegramClient, chatService));
  }

  @Override
  public Receive<StartCommandHandlerActor.Api> createReceive() {
    return newReceiveBuilder()
      .onMessage(StartCommandHandlerActor.Api.StartCommand.class, this::handleStart)
      .build();
  }

  private Behavior<StartCommandHandlerActor.Api> handleStart(StartCommandHandlerActor.Api.StartCommand startCommand) {
    final Chat chat = startCommand.chat;
    final Long chatId = chat.getId();
    getContext().getLog().info("Вызвана команда '/start' в чате '{}'.", chat.getId());
    chatService.newChat(chat.getId(), chat.getType(), chat.getTitle()).thenAcceptAsync(c -> {
      telegramClient.tell(new SendMessage(chatId.toString(), "Привет, для тебя создан чат `" + chatId + "`."));
    });
    return Behaviors.stopped();
  }
}

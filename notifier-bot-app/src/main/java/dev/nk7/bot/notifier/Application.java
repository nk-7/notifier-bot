package dev.nk7.bot.notifier;


import dev.nk7.bot.notifier.api.SendNotificationRequest;
import dev.nk7.bot.notifier.core.usecase.AddNewChatUseCase;
import dev.nk7.bot.notifier.core.usecase.SendNotificationUseCase;
import dev.nk7.bot.notifier.persistence.rocksdb.Rocks;
import dev.nk7.bot.notifier.persistence.rocksdb.RocksProperties;
import dev.nk7.bot.notifier.telegram.TelegramBot;
import dev.nk7.bot.notifier.telegram.service.MessageService;
import io.javalin.Javalin;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class Application {


  public static void main(String[] args) throws Exception {
    final ApplicationProperties applicationProperties = new ApplicationProperties();
    final Rocks rocks = new Rocks(new RocksProperties(applicationProperties.getRocksDbPath()));

    final TelegramClient telegramClient = new OkHttpTelegramClient(applicationProperties.getTelegramToken());
    final MessageService messageService = new MessageService(telegramClient);
    final SendNotificationUseCase sendNotificationUseCase = new SendNotificationUseCase(messageService, rocks.repos().chatRepository());

    final Javalin javalin = Javalin.create();
    javalin.post("/api/v1/notification", ctx -> {
      final SendNotificationRequest request = ctx.bodyStreamAsClass(SendNotificationRequest.class);
      sendNotificationUseCase.sendNotification(request.text(), request.tags());
    });

    final StartCommandHandler startCommandHandler = new StartCommandHandler(new AddNewChatUseCase(rocks.repos().chatRepository(), messageService));
    final TelegramBot telegramBot = new TelegramBot(applicationProperties.getTelegramToken(), startCommandHandler);
    telegramBot.start();
    javalin.start(applicationProperties.getHttPort());


  }

}


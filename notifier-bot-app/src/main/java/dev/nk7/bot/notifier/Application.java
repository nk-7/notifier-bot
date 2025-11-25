package dev.nk7.bot.notifier;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dev.nk7.bot.notifier.api.SendNotificationRequest;
import dev.nk7.bot.notifier.core.usecase.AddNewChatUseCase;
import dev.nk7.bot.notifier.core.usecase.GetChatsUseCase;
import dev.nk7.bot.notifier.core.usecase.SendNotificationUseCase;
import dev.nk7.bot.notifier.persistence.rocksdb.Rocks;
import dev.nk7.bot.notifier.persistence.rocksdb.RocksProperties;
import dev.nk7.bot.notifier.telegram.TelegramBot;
import dev.nk7.bot.notifier.telegram.service.MessageService;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import io.javalin.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class Application {
  private static final Logger log = LoggerFactory.getLogger(Application.class);


  public static void main(String[] args) throws Exception {
    final ApplicationProperties applicationProperties = new ApplicationProperties();
    final Rocks rocks = new Rocks(new RocksProperties(applicationProperties.getRocksDbPath()));

    final TelegramClient telegramClient = new OkHttpTelegramClient(applicationProperties.getTelegramToken());
    final MessageService messageService = new MessageService(telegramClient);
    final SendNotificationUseCase sendNotificationUseCase = new SendNotificationUseCase(messageService, rocks.repos().chatRepository());
    final GetChatsUseCase getChatsUseCase = new GetChatsUseCase(rocks.repos().chatRepository());

    final Javalin javalin = javalin();

    javalin.post("/api/v1/notification", ctx -> {
        final SendNotificationRequest request = ctx.bodyStreamAsClass(SendNotificationRequest.class);
        sendNotificationUseCase.sendNotification(request.text(), request.tags());
      })
      .get("/console/chats", ctx -> {
        ctx.contentType("application/json");
        ctx.json(getChatsUseCase.getChats());
      });

    final StartCommandHandler startCommandHandler = new StartCommandHandler(new AddNewChatUseCase(rocks.repos().chatRepository(), messageService));
    final TelegramBot telegramBot = new TelegramBot(applicationProperties.getTelegramToken(), startCommandHandler);
    telegramBot.start();
    log.info("Starting REST API");
    javalin.start(applicationProperties.getHttPort());
    Runtime.getRuntime().addShutdownHook(new Thread(javalin::stop));
    log.info("Application started!");
  }


  private static Javalin javalin() {
    final JsonMapper jsonMapper = new JavalinJackson(
      new ObjectMapper()
        .enable(SerializationFeature.INDENT_OUTPUT),
      false
    );
    return Javalin.create(cfg -> {
      cfg.jsonMapper(jsonMapper);
    });
  }

}


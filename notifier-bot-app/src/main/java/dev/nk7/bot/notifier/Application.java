package dev.nk7.bot.notifier;


import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import dev.nk7.bot.notifier.api.SendNotificationRequest;
import dev.nk7.bot.notifier.core.port.out.ChatRepository;
import dev.nk7.bot.notifier.core.usecase.AddNewChatUseCase;
import dev.nk7.bot.notifier.core.usecase.SendNotificationUseCase;
import dev.nk7.bot.notifier.persistence.RocksChatRepository;
import dev.nk7.bot.notifier.persistence.rocksdb.Entities;
import dev.nk7.bot.notifier.persistence.rocksdb.Rocks;
import dev.nk7.bot.notifier.persistence.rocksdb.RocksProperties;
import dev.nk7.bot.notifier.persistence.serialization.JacksonSerializer;
import dev.nk7.bot.notifier.persistence.serialization.Serializer;
import dev.nk7.bot.notifier.telegram.MessageService;
import dev.nk7.bot.notifier.telegram.UpdateEvent;
import io.javalin.Javalin;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.Objects;

public class Application implements Runnable {

  private final Javalin javalin;
  private final ApplicationProperties applicationProperties;
  private final Rocks<Entities> rocks;
  private final Serializer serializer;
  private final Disruptor<UpdateEvent> disruptor;


  public Application(ApplicationProperties applicationProperties, Rocks<Entities> rocks, Serializer serializer, Disruptor<UpdateEvent> disruptor) throws Exception {
    this.applicationProperties = Objects.requireNonNull(applicationProperties);
    this.rocks = Objects.requireNonNull(rocks);
    this.serializer = Objects.requireNonNull(serializer);
    this.disruptor = Objects.requireNonNull(disruptor);
    this.javalin = Javalin.create();
    configure();
  }

  private void configure() throws TelegramApiException {

    final LongPollingSingleThreadUpdateConsumer updateConsumer = new SendDisruptorUpdateConsumer(disruptor);
    final TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
    botsApplication.registerBot(applicationProperties.getTelegramToken(), updateConsumer);

    final TelegramClient telegramClient = new OkHttpTelegramClient(applicationProperties.getTelegramToken());
    final ChatRepository chatRepository = new RocksChatRepository(rocks, serializer);
    final MessageService messageService = new MessageService(telegramClient);

    final StartCommandHandler startCommandHandler = new StartCommandHandler(new AddNewChatUseCase(chatRepository, messageService));
    disruptor.handleEventsWith(startCommandHandler);
    final SendNotificationUseCase sendNotificationUseCase = new SendNotificationUseCase(messageService, chatRepository);


    javalin.post("/api/v1/notification", ctx -> {
      final SendNotificationRequest request = ctx.bodyStreamAsClass(SendNotificationRequest.class);
      sendNotificationUseCase.sendNotification(request.text(), request.tags());
    });

  }

  @Override
  public void run() {
    javalin.start(applicationProperties.getHttPort());
    disruptor.start();
  }

  public static void main(String[] args) throws Exception {
    final ApplicationProperties applicationProperties = new ApplicationProperties();
    final Rocks<Entities> rocks = new Rocks<>(new RocksProperties(applicationProperties.getRocksDbPath()), Entities.class);
    final Serializer serializer = new JacksonSerializer();
    final Disruptor<UpdateEvent> disruptor = new Disruptor<>(UpdateEvent::new,
      64,
      DaemonThreadFactory.INSTANCE,
      ProducerType.SINGLE,
      new BlockingWaitStrategy()
    );
    final Application application = new Application(applicationProperties, rocks, serializer, disruptor);
    application.run();

  }

  private static class SendDisruptorUpdateConsumer implements LongPollingSingleThreadUpdateConsumer {

    private final Disruptor<UpdateEvent> disruptor;

    public SendDisruptorUpdateConsumer(Disruptor<UpdateEvent> disruptor) {
      this.disruptor = Objects.requireNonNull(disruptor);
    }

    @Override
    public void consume(Update update) {
      disruptor.getRingBuffer().publishEvent((event, sequence) -> event.setUpdate(update));
    }
  }

}


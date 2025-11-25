package dev.nk7.bot.notifier;


import dev.nk7.bot.notifier.modules.*;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
  private static final Logger log = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) throws Exception {
    final ApplicationProperties applicationProperties = new ApplicationProperties();

    final RepositoriesModule repositories = RepositoriesModule.of(applicationProperties);
    final ServicesModule services = ServicesModule.of(applicationProperties);
    final UseCasesModule useCases = UseCasesModule.of(repositories, services);
    final TelegramBotModule telegramBotModule = TelegramBotModule.of(applicationProperties, useCases);
    final RestApiModule restApiModule = RestApiModule.of(useCases);

    telegramBotModule.telegramBot().start();
    final Javalin javalin = restApiModule.javalin();
    log.info("Starting REST API");
    javalin.start(applicationProperties.getHttPort());
    Runtime.getRuntime().addShutdownHook(new Thread(javalin::stop));
    log.info("Application started!");
  }
}


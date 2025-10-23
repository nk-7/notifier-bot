package dev.nk7.bot.notifier.infra;

import dev.nk7.bot.notifier.api.SendNotificationRequest;
import io.javalin.Javalin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestConfig {


  @Bean(destroyMethod = "stop")
  Javalin javalin() {
    final Javalin api = Javalin.create();
    api.get("/api/v1/notification", ctx -> {
//      final SendNotificationRequest request = ctx.bodyStreamAsClass(SendNotificationRequest.class);
//      System.out.println(request);
    });
    api.start(8080);
    return api;
  }
}

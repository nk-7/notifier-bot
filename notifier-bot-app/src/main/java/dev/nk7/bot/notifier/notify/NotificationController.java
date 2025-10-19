package dev.nk7.bot.notifier.notify;

import dev.nk7.bot.notifier.api.SendNotificationRequest;
import dev.nk7.bot.notifier.core.port.in.SendNotificationUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

  private final SendNotificationUseCase sendNotificationUseCase;

  public NotificationController(SendNotificationUseCase sendNotificationUseCase) {
    this.sendNotificationUseCase = Objects.requireNonNull(sendNotificationUseCase);
  }

  @PostMapping
  ResponseEntity<Void> postNotification(@RequestBody SendNotificationRequest request) {
    sendNotificationUseCase.sendNotification(request.text(), request.tags());
    return ResponseEntity.ok().build();
  }
}

package dev.nk7.bot.notifier.engine;

import org.telegram.telegrambots.meta.api.objects.Update;

public class UpdateEvent {

  private Update update;

  public Update getUpdate() {
    return update;
  }

  public void setUpdate(Update update) {
    this.update = update;
  }

  @Override
  public String toString() {
    return "UpdateEvent{" +
      "update=" + update +
      '}';
  }
}

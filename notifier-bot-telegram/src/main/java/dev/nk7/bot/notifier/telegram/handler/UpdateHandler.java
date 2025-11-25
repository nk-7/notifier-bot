package dev.nk7.bot.notifier.telegram.handler;

import com.lmax.disruptor.EventHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class UpdateHandler implements EventHandler<UpdateEvent> {
  @Override
  public void onEvent(UpdateEvent event, long sequence, boolean endOfBatch) {
    final Update update = event.getUpdate();
    if (filter(update)) {
      handle(update);
    }
  }


  protected abstract boolean filter(Update update);

  protected abstract void handle(Update update);


}

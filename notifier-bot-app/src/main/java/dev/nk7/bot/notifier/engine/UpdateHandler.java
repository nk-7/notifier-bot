package dev.nk7.bot.notifier.engine;

import com.lmax.disruptor.EventHandler;

public abstract class UpdateHandler implements EventHandler<UpdateEvent> {
  @Override
  public void onEvent(UpdateEvent event, long sequence, boolean endOfBatch) throws Exception {
    if (filter(event)) {
      handle(event);
    }
  }


  protected abstract boolean filter(UpdateEvent event);

  protected abstract void handle(UpdateEvent event);


}

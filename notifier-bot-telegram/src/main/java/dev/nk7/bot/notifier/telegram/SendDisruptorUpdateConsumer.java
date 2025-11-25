package dev.nk7.bot.notifier.telegram;

import com.lmax.disruptor.dsl.Disruptor;
import dev.nk7.bot.notifier.telegram.handler.UpdateEvent;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

record SendDisruptorUpdateConsumer(Disruptor<UpdateEvent> disruptor) implements LongPollingSingleThreadUpdateConsumer {

  @Override
  public void consume(Update update) {
    disruptor.getRingBuffer().publishEvent((event, sequence) -> event.setUpdate(update));
  }
}

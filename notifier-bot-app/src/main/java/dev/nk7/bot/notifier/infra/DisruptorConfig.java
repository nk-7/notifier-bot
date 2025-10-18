package dev.nk7.bot.notifier.infra;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import dev.nk7.bot.notifier.telegram.UpdateEvent;
import org.springframework.context.annotation.Bean;

import java.util.List;

public class DisruptorConfig {

  @Bean(destroyMethod = "shutdown", initMethod = "start")
  Disruptor<UpdateEvent> disruptor(List<EventHandler<UpdateEvent>> handlers) {
    final Disruptor<UpdateEvent> disruptor = new Disruptor<>(UpdateEvent::new,
      64,
      DaemonThreadFactory.INSTANCE,
      ProducerType.SINGLE,
      new BlockingWaitStrategy()
    );
    handlers.forEach(disruptor::handleEventsWith);
    return disruptor;
  }
}

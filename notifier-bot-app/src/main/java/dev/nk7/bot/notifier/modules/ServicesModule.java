package dev.nk7.bot.notifier.modules;

import dev.nk7.bot.notifier.ApplicationProperties;
import dev.nk7.bot.notifier.core.port.out.service.MessageService;

public interface ServicesModule {

  MessageService messageService();


  static ServicesModule of(ApplicationProperties applicationProperties) {
    return new ServicesModuleImpl(applicationProperties);
  }

}

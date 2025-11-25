package dev.nk7.bot.notifier.modules;

import dev.nk7.bot.notifier.ApplicationProperties;
import dev.nk7.bot.notifier.core.port.out.repository.ChatRepository;

public interface RepositoriesModule {

  ChatRepository chatRepository();

  static RepositoriesModule of(ApplicationProperties applicationProperties) {
    return new RepositoriesModuleImpl(applicationProperties);
  }
}

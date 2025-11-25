package dev.nk7.bot.notifier.modules;

import dev.nk7.bot.notifier.core.port.in.AddNewChatUseCase;
import dev.nk7.bot.notifier.core.port.in.GetChatsUseCase;
import dev.nk7.bot.notifier.core.port.in.SendNotificationUseCase;

public interface UseCasesModule {

  SendNotificationUseCase sendNotificationUseCase();

  AddNewChatUseCase addNewChatUseCase();

  GetChatsUseCase getChatsUseCase();
  static UseCasesModule of(RepositoriesModule repositoriesModule, ServicesModule servicesModule) {
    return new UseCasesModuleImpl(repositoriesModule, servicesModule);
  }
}

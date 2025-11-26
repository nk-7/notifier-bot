package dev.nk7.bot.notifier.modules;

import dev.nk7.bot.notifier.core.port.in.AddNewChatUseCase;
import dev.nk7.bot.notifier.core.port.in.ChangeChatStatusUseCase;
import dev.nk7.bot.notifier.core.port.in.GetChatsUseCase;
import dev.nk7.bot.notifier.core.port.in.SendNotificationUseCase;

import java.util.Objects;

class UseCasesModuleImpl implements UseCasesModule {


  private final RepositoriesModule repositoriesModule;

  private final ServicesModule servicesModule;

  UseCasesModuleImpl(RepositoriesModule repositoriesModule, ServicesModule servicesModule) {
    this.repositoriesModule = Objects.requireNonNull(repositoriesModule);
    this.servicesModule = Objects.requireNonNull(servicesModule);
  }


  @Override
  public SendNotificationUseCase sendNotificationUseCase() {
    return new dev.nk7.bot.notifier.core.usecase.SendNotificationUseCase(
      servicesModule.messageService(),
      repositoriesModule.chatRepository()
    );
  }

  @Override
  public AddNewChatUseCase addNewChatUseCase() {
    return new dev.nk7.bot.notifier.core.usecase.AddNewChatUseCase(
      repositoriesModule.chatRepository(),
      servicesModule.messageService()
    );
  }

  @Override
  public ChangeChatStatusUseCase changeChatStatusUseCase() {
    return new dev.nk7.bot.notifier.core.usecase.ChangeChatStatusUseCase(
      repositoriesModule.chatRepository()
    );
  }

  @Override
  public GetChatsUseCase getChatsUseCase() {
    return new dev.nk7.bot.notifier.core.usecase.GetChatsUseCase(repositoriesModule.chatRepository());
  }

}

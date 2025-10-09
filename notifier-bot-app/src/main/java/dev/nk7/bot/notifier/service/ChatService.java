package dev.nk7.bot.notifier.service;

import dev.nk7.bot.notifier.entities.Chat;

import java.util.concurrent.CompletableFuture;

public interface ChatService {


  CompletableFuture<Chat> newChat(Long chatId, String type, String title);
}

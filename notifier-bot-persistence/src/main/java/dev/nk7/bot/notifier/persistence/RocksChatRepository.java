package dev.nk7.bot.notifier.persistence;

import dev.nk7.bot.notifier.core.model.Chat;
import dev.nk7.bot.notifier.core.port.out.ChatRepository;
import dev.nk7.bot.notifier.persistence.rocksdb.Entities;
import dev.nk7.bot.notifier.persistence.rocksdb.Rocks;
import dev.nk7.bot.notifier.persistence.rocksdb.RocksRepository;
import dev.nk7.bot.notifier.persistence.serialization.Serializer;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RocksChatRepository extends RocksRepository<Entities> implements ChatRepository {

  public RocksChatRepository(Rocks<Entities> rocks, Serializer serializer) {
    super(rocks, serializer);
  }

  @Override
  public void save(Chat chat) {
    final byte[] key = serializer.toBytes(chat.chatId());
    final byte[] value = serializer.toBytes(chat);
    rocks.put(Entities.CHAT, key, value);
  }

  @Override
  public Optional<Chat> findByChatId(Long chatId) {
    final byte[] key = serializer.toBytes(chatId);
    return Optional.ofNullable(rocks.get(Entities.CHAT, key))
      .map(bytes -> serializer.fromBytes(bytes, Chat.class));
  }

  @Override
  public Set<Chat> getFiltered(Predicate<Chat> filter) {
    final List<byte[]> all = rocks.getAll(Entities.CHAT);
    if (all == null || all.isEmpty()) {
      return Collections.emptySet();
    }
    return all.stream()
      .map(bytes -> serializer.fromBytes(bytes, Chat.class))
      .collect(Collectors.toUnmodifiableSet());
  }
}

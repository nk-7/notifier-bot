package dev.nk7.bot.notifier.persistence.serialization;

public interface Serializer {

  byte[] toBytes(Object object) throws SerializationException;

  <T> T fromBytes(byte[] bytes, Class<T> clazz) throws SerializationException;
}

package dev.nk7.bot.notifier.persistence.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JacksonSerializer implements Serializer {
  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  public byte[] toBytes(Object object) throws SerializationException {
    try {
      return mapper.writeValueAsBytes(object);
    } catch (JsonProcessingException e) {
      throw new SerializationException(e);
    }
  }

  @Override
  public <T> T fromBytes(byte[] bytes, Class<T> clazz) {
    try {
      return mapper.readValue(bytes, clazz);
    } catch (IOException e) {
      throw new SerializationException(e);
    }

  }
}

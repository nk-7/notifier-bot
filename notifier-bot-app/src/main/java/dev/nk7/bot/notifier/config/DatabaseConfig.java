package dev.nk7.bot.notifier.config;

import dev.nk7.bot.notifier.config.properties.RocksDbProperties;
import dev.nk7.bot.notifier.core.port.out.ChatRepository;
import dev.nk7.bot.notifier.persistence.RocksChatRepository;
import dev.nk7.bot.notifier.persistence.rocksdb.Entities;
import dev.nk7.bot.notifier.persistence.rocksdb.Rocks;
import dev.nk7.bot.notifier.persistence.rocksdb.RocksProperties;
import dev.nk7.bot.notifier.persistence.serialization.JacksonSerializer;
import dev.nk7.bot.notifier.persistence.serialization.Serializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RocksDbProperties.class)
public class DatabaseConfig {


  @Bean
  Rocks<Entities> rocks(RocksDbProperties rocksDbProperties) {
    final RocksProperties rocksProperties = new RocksProperties(rocksDbProperties.path());
    return new Rocks<>(rocksProperties, Entities.class);
  }

  @Bean
  Serializer serializer() {
    return new JacksonSerializer();
  }

  @Bean
  ChatRepository chatRepository(Rocks<Entities> rocks, Serializer serializer) {
    return new RocksChatRepository(rocks, serializer);
  }

}

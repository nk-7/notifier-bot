package dev.nk7.bot.notifier;

import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.EnvironmentConfiguration;
import org.apache.commons.configuration2.SystemConfiguration;
import org.apache.commons.configuration2.YAMLConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.InputStream;

public class ApplicationProperties {

  private static final String APPLICATION_YAML = "application.yaml";
  private final CompositeConfiguration config = new CompositeConfiguration();

  public ApplicationProperties() throws ConfigurationException {
    config.addConfiguration(new EnvironmentConfiguration());
    config.addConfiguration(new SystemConfiguration());
    final InputStream applicationYaml = Thread.currentThread().getContextClassLoader().getResourceAsStream(APPLICATION_YAML);
    if (applicationYaml != null) {
      final YAMLConfiguration yamlConfiguration = new YAMLConfiguration();
      yamlConfiguration.read(applicationYaml);
      config.addConfiguration(yamlConfiguration);
    }
  }


  public String getRocksDbPath() {
    return config.getString("rocksdb.path");
  }

  public String getTelegramToken() {
    return config.getString("bot.token");
  }

  public int getHttPort() {
    return config.getInt("http.port", 8080);
  }
}

plugins {
  alias(libs.plugins.jib)
}

dependencies {
  implementation(project(":notifier-bot-core"))
  implementation(project(":notifier-bot-persistence"))
  implementation(project(":notifier-bot-telegram"))
  implementation(project(":notifier-bot-api"))
  implementation(libs.commons.configuration)
  implementation(libs.snakeyaml)
  implementation(libs.telegrambots.longpolling)
  implementation(libs.telegrambots.client)

  implementation(libs.javalin)
  compileOnly(libs.slf4j)
  runtimeOnly(libs.logback.classic)

  implementation(libs.disruptor)

  testImplementation(platform(libs.bom.junit))
  testImplementation(libs.junit.jupiter)
  testRuntimeOnly(libs.junit.jupiter.launcher)
  testImplementation(libs.assertj)

}

jib {
  from {
    image = "eclipse-temurin:25.0.1_8-jre-ubi10-minimal"
    platforms {
      platform {
        architecture = "arm64"
        os = "linux"
      }
      platform {
        architecture = "amd64"
        os = "linux"

      }
    }
  }
  to {
    image = "registry.nk7.dev/notifier-bot"
    tags = setOf("${project.version}")
    auth {
      username = extra.properties["registry.nk7.dev.username"].toString()
      password = extra.properties["registry.nk7.dev.password"].toString()
    }
  }
  container {
    jvmFlags = listOf("-Xms64m", "-Xmx64m", "--enable-native-access=ALL-UNNAMED")
    ports = listOf("8080")
    volumes = listOf("/database")
    environment = mapOf(
      "rocksdb.path" to "/database",
      "http.port" to "8080",
      "TZ" to "Europe/Moscow"
    )
    labels = mapOf(
      "version" to project.version.toString()
    )
  }

}

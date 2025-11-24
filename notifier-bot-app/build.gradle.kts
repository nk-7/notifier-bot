plugins {
  alias(libs.plugins.spring.boot)
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

plugins {
  alias(libs.plugins.spring.boot)
}
dependencies {
  implementation(platform(libs.bom.spring.boot))
  implementation(project(":notifier-bot-core"))
  implementation(project(":notifier-bot-persistence"))
  implementation(libs.spring.boot.starter.web)
  annotationProcessor(libs.spring.boot.configuration.processor)
  implementation(libs.telegrambots.longpolling)
  implementation(libs.telegrambots.client)
  compileOnly(libs.slf4j)
  runtimeOnly(libs.logback.classic)

  implementation(libs.disruptor)

  testImplementation(platform(libs.bom.junit))
  testImplementation(libs.junit.jupiter)
  testRuntimeOnly(libs.junit.jupiter.launcher)
  testImplementation(libs.assertj)

}

plugins {
  alias(libs.plugins.spring.boot)
}
dependencies {

  implementation(platform(libs.bom.spring.boot))
  implementation(platform(libs.bom.akka))
  implementation(project(":notifier-bot-persistence"))
  implementation(project(":notifier-bot-model"))
  implementation(libs.spring.boot.starter.web)
  annotationProcessor(libs.spring.boot.configuration.processor)
  implementation(libs.akka.actor.typed)
  implementation(libs.database.h2.r2dbc)
  implementation(libs.telegrambots.longpolling)
  implementation(libs.telegrambots.client)
  compileOnly(libs.slf4j)
  runtimeOnly(libs.logback.classic)


  testImplementation(libs.junit.jupiter)
  testImplementation(project(":notifier-bot-database"))
  testRuntimeOnly(libs.junit.jupiter.launcher)
  testImplementation(libs.assertj)

}

plugins {
  alias(libs.plugins.spring.boot)
}
dependencies {

  implementation(platform(libs.bom.spring.boot))
  implementation(libs.spring.boot.starter.web)
  implementation(platform(libs.bom.akka))
  annotationProcessor(libs.spring.boot.configuration.processor)
  implementation(libs.akka.actor.typed)

  implementation(libs.telegrambots.longpolling)
  implementation(libs.telegrambots.client)

  compileOnly(libs.slf4j)
  runtimeOnly(libs.logback.classic)


  testImplementation(libs.junit.jupiter)
  testRuntimeOnly(libs.junit.jupiter.launcher)
}

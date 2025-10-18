plugins {
  id("java-library")
}

dependencies {
  implementation(project(":notifier-bot-core"))

  implementation(libs.telegrambots.longpolling)
  implementation(libs.telegrambots.client)
  compileOnly(libs.slf4j)

  compileOnly(libs.disruptor)

  testImplementation(platform(libs.bom.junit))
  testImplementation(libs.junit.jupiter)
  testRuntimeOnly(libs.junit.jupiter.launcher)
  testImplementation(libs.assertj)
}

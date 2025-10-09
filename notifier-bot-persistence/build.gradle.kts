plugins {
  id("java")
}
dependencies {

  implementation(platform(libs.bom.spring.boot))
  implementation(libs.database.h2.r2dbc)

  compileOnly(libs.slf4j)

  testImplementation(libs.junit.jupiter)
  testImplementation(project(":notifier-bot-database"))
  testRuntimeOnly(libs.junit.jupiter.launcher)
  testImplementation(libs.assertj)

}

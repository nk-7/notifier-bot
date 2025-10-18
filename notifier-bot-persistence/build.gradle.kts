plugins {
  id("java-library")
}

dependencies {

  implementation(platform(libs.bom.spring.boot))

  implementation(libs.jackson.databind)
  implementation(project(":notifier-bot-core"))
  implementation(libs.database.rocksdb)

  testImplementation(platform(libs.bom.junit))
  testImplementation(libs.junit.jupiter)
  testImplementation(libs.assertj)
  testRuntimeOnly(libs.junit.jupiter.launcher)

}

tasks.test {
  useJUnitPlatform()
}


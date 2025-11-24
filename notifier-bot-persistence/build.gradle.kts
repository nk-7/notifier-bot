plugins {
  id("java-library")
}

dependencies {
  implementation(platform(libs.bom.jackson))
  implementation(libs.jackson.databind)
  implementation(project(":notifier-bot-core"))
  implementation(libs.database.rocksdb)

  compileOnly(libs.slf4j)

  testImplementation(platform(libs.bom.junit))
  testImplementation(libs.junit.jupiter)
  testImplementation(libs.assertj)
  testRuntimeOnly(libs.junit.jupiter.launcher)

}

tasks.test {
  useJUnitPlatform()
}


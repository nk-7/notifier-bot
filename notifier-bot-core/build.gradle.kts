plugins {
  id("java-library")
}

group = "dev.nk7"
version = "1.0.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  testImplementation(platform(libs.bom.junit))
  compileOnly(libs.slf4j)
  testImplementation(libs.junit.jupiter)
  testRuntimeOnly(libs.junit.jupiter.launcher)
}

tasks.test {
  useJUnitPlatform()
}

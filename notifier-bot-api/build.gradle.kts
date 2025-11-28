plugins {
  id("java-library")
}

dependencies {
  testImplementation(platform(libs.bom.junit))
  compileOnly(libs.slf4j)
  testImplementation(libs.junit.jupiter)
  testRuntimeOnly(libs.junit.jupiter.launcher)
}

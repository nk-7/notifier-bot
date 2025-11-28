plugins {
  id("java-library")
  alias(libs.plugins.jib)
}

dependencies {
  testImplementation(platform(libs.bom.junit))
  compileOnly(libs.slf4j)
  testImplementation(libs.junit.jupiter)
  testRuntimeOnly(libs.junit.jupiter.launcher)
}

//jib {
//  from {
//    image = "eclipse-temurin:25.0.1_8-jre-ubi10-minimal"
//  }
//  to {
//    image = "registry.nk7.dev/notifier-bot:latest"
//    tags = setOf("${project.version}")
//  }
//
//}

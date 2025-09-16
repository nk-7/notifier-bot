plugins {
  id("java")
}
dependencies {
  implementation(platform(libs.bom.spring.boot))
  implementation(libs.database.liquibase)
  implementation(libs.slf4j)
  runtimeOnly(libs.logback.classic)
  runtimeOnly(libs.database.h2)

}


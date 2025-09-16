plugins {
  id("java")
}
dependencies {
  implementation(platform(libs.bom.spring.boot))
  implementation(libs.database.liquibase)
  runtimeOnly(libs.database.h2)

}


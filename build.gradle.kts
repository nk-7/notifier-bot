plugins {
  id("java")
  alias(libs.plugins.jib) apply false
}
allprojects {
  group = "dev.nk7"
  version = "1.0.0-SNAPSHOT"

  repositories {
    mavenCentral()
  }
}
subprojects {
  group = project.group
  version = project.version

  apply {
    plugin("java")
  }
  val javaVersion = 25
  tasks {
    compileJava {
      options.compilerArgs.add("-parameters")
    }
    java {
      toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
      }
    }
    test {
      useJUnitPlatform()
    }
  }
}


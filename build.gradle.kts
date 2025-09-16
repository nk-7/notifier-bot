plugins {
  id("java")
}
allprojects {
  group = "dev.nk7"
  version = "1.0.0-SNAPSHOT"
}

repositories {
  mavenCentral()
}

tasks {
  test {
    useJUnitPlatform()
  }
}

subprojects {
  group = project.group

  apply {
    plugin("java")
  }
  val javaVersion = 21
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


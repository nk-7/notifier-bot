rootProject.name = "notifier-bot"

dependencyResolutionManagement {
  repositories {
    maven {
      url = uri(extra.properties["repo.snapshots.url"].toString())
      credentials.username = extra.properties["repo.username"].toString()
      credentials.password = extra.properties["repo.password"].toString()
    }
    maven {
      url = uri(extra.properties["repo.releases.url"].toString())
      credentials.username = extra.properties["repo.username"].toString()
      credentials.password = extra.properties["repo.password"].toString()
    }
  }
}

include("notifier-bot-app")
include("notifier-bot-database")

include("notifier-bot-persistence")
include("notifier-bot-model")
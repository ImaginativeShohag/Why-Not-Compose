pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://jitpack.io")
    }
}
rootProject.name = "Why Not Compose!"
include(":app")
include(":tictactoe")
include(":common-ui-compose")
include(":base")
include(":exoplayer")
include(":cms")

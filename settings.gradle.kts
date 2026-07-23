@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://jitpack.io")
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

plugins {
    // See https://splitties.github.io/refreshVersions/
    id("de.fayard.refreshVersions") version "0.60.6"

    // See https://docs.gradle.com/develocity/gradle-plugin/current/
    id("com.gradle.develocity").version("3.19")
////                        # available:"3.19.1")
////                        # available:"3.19.2")
////                        # available:"4.0")
////                        # available:"4.0.1")
////                        # available:"4.0.2")
////                        # available:"4.0.3")
////                        # available:"4.1")
////                        # available:"4.1.1")
////                        # available:"4.2")
////                        # available:"4.2.1")
////                        # available:"4.2.2")
////                        # available:"4.3")
////                        # available:"4.3.1")
////                        # available:"4.3.2")
////                        # available:"4.3.3")
////                        # available:"4.4.0")
////                        # available:"4.4.1")
////                        # available:"4.4.2")
////                        # available:"4.4.3")
////                        # available:"4.5.0")
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
////                                                   # available:"1.0.0-rc-1"
////                                                   # available:"1.0.0"
}

refreshVersions {
    rejectVersionIf {
        candidate.stabilityLevel.isLessStableThan(current.stabilityLevel)
    }
}

develocity {
    buildScan {
        termsOfUseUrl.set("https://gradle.com/help/legal-terms-of-use")
        termsOfUseAgree.set("yes")
        publishing.onlyIf { it.buildResult.failures.isNotEmpty() }
    }
}

rootProject.name = "Why Not Compose!"
include(":app")
include(":base")
include(":common-ui-compose")
include(":tictactoe")
include(":exoplayer")
include(":cms")
include(":popbackstack")
include(":benchmarks")

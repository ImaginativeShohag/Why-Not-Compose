// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
    dependencies {
        classpath(Libs.androidGradlePlugin)

        classpath(Libs.Google.service)

        classpath(Libs.Kotlin.gradlePlugin)

        classpath(Libs.OneSignal.gradlePlugin)

        classpath(Libs.Hilt.androidGradlePlugin)

        classpath(Libs.secretsGradlePlugin)
    }
}

plugins {
    id("com.diffplug.spotless") version "6.2.2"
}

subprojects {
    apply(plugin = "com.diffplug.spotless")

    spotless {
        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            targetExclude("bin/**/*.kt")

            ktlint("0.43.2").userData(
                mapOf("disabled_rules" to "filename")
            )
            licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
        }
    }
}

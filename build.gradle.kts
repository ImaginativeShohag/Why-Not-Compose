// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://jitpack.io")
    }
}

plugins {
    id(Libs.Android.application) version Libs.Gradle.version apply false
    id(Libs.Android.library) version Libs.Gradle.version apply false
    kotlin("android") version Libs.Kotlin.version apply false
    id(Libs.Google.Firebase.crashlyticsGradlePlugin) version Libs.Google.Firebase.crashlyticsGradlePluginVersion apply false
    id(Libs.Google.Services.gradlePlugin) version Libs.Google.Services.version apply false
    id(Libs.Google.Maps.secretsGradlePlugin) version Libs.Google.Maps.secretsGradlePluginVersion apply false
    id(Libs.Google.Hilt.gradlePlugin) version Libs.Google.Hilt.version apply false

    id(Libs.DiffPlug.spotless) version Libs.DiffPlug.version
}

subprojects {
    apply(plugin = Libs.DiffPlug.spotless)

    spotless {
        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            targetExclude("bin/**/*.kt")

            ktlint("0.49.1").editorConfigOverride(
                mapOf(
                    "ktlint_code_style" to "android",
                    "max_line_length" to "off",
                    "standard:comment-wrapping" to "off"
                )
            )
            licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
        }
    }
}

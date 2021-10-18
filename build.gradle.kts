// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Libs.androidGradlePlugin)
        classpath(Libs.Kotlin.gradlePlugin)

        classpath(Libs.Hilt.androidGradlePlugin)

        classpath(Libs.secretsGradlePlugin)
    }
}

plugins {
    id("com.diffplug.spotless") version "5.17.0"
}

subprojects {
    apply(plugin = "com.diffplug.spotless")

    spotless {
//        isEnforceCheck = false

        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            targetExclude("bin/**/*.kt")

            ktlint(Versions.ktlint)
            // licenseHeaderFile rootProject.file('spotless/copyright.kt')
        }
    }
}

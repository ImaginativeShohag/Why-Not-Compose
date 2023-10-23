/*
 * Copyright 2023 Md. Mahmudul Hasan Shohag
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ------------------------------------------------------------------------
 *
 * Project: Why Not Compose!
 * Developed by: @ImaginativeShohag
 *
 * Md. Mahmudul Hasan Shohag
 * imaginativeshohag@gmail.com
 *
 * Source: https://github.com/ImaginativeShohag/Why-Not-Compose
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
    id(Libs.Google.DevTools.ksp) version Libs.Google.DevTools.kspVersion apply false
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

            ktlint("1.0.1")
            licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
        }

        format("kts") {
            target("**/*.kts")
            targetExclude("**/build/**/*.kts")
            // Look for the first line that doesn't have a block comment (assumed to be the license)
            licenseHeaderFile(rootProject.file("spotless/copyright.kts"), "(^(?![\\/ ]\\*).*$)")
        }

        format("xml") {
            target("**/*.xml")
            targetExclude("**/build/**/*.xml")
            // Look for the first XML tag that isn't a comment (<!--) or the xml declaration (<?xml)
            licenseHeaderFile(rootProject.file("spotless/copyright.xml"), "(<[^!?])")
        }
    }
}

allprojects {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}

task("testClasses").doLast {
    println("This is a dummy testClasses task to fix GitHub CodeQL action issue.")
}

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

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
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

/*
  id(Libs.Android.application) version Libs.Gradle.version apply false
    id(Libs.Android.library) version Libs.Gradle.version apply false
    kotlin("android") version Libs.Kotlin.version apply false
    kotlin("plugin.serialization") version Libs.Kotlin.version apply false
    id(Libs.Kotlin.composeCompilerGradlePlugin) version Libs.Kotlin.version apply false
    id(Libs.Google.DevTools.ksp) version Libs.Google.DevTools.kspVersion apply false
    id(Libs.Google.Firebase.crashlyticsGradlePlugin) version Libs.Google.Firebase.crashlyticsGradlePluginVersion apply false
    id(Libs.Google.Services.gradlePlugin) version Libs.Google.Services.version apply false
    id(Libs.Google.Maps.secretsGradlePlugin) version Libs.Google.Maps.secretsGradlePluginVersion apply false
    id(Libs.Google.Hilt.gradlePlugin) version Libs.Google.Hilt.version apply false

    id(Libs.DiffPlug.spotless) version Libs.DiffPlug.version
 */

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.secrets) apply false
    alias(libs.plugins.hilt) apply false
    // alias(libs.plugins.spotless) apply false
}

allprojects {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }
}

task("testClasses").doLast {
    println("This is a dummy testClasses task to fix GitHub CodeQL action issue.")
}

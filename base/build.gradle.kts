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

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "org.imaginativeworld.whynotcompose.base"
    compileSdk = BuildConfigConst.compileSdk

    defaultConfig {
        minSdk = BuildConfigConst.minSdk
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)

    // ----------------------------------------------------------------
    // Compose
    // ----------------------------------------------------------------
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.util)
    // Tooling support (Previews, etc.)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    // Animation
    implementation(libs.androidx.compose.animation)
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    // Material Design
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    // Material design icons
    implementation(libs.androidx.compose.material.iconsCore)
    implementation(libs.androidx.compose.material.iconsExtended)
    // Integration with observables
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.runtime.livedata)
    implementation(libs.androidx.compose.runtime.tracing)
    // compose Navigation Component
    implementation(libs.androidx.navigation.compose)
    // Constraint Layout
    implementation(libs.androidx.constraintlayout.compose)
    // Integration with activities
    implementation(libs.androidx.activity.compose)

    // Jetpack compose Integration for ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Paging
    implementation(libs.androidx.paging.compose)

    // ----------------------------------------------------------------

    // Retrofit
    implementation(libs.retrofit.core)
    implementation(libs.okhttp.logging)

    // Moshi
    implementation(libs.retrofit.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)

    // Timber
    implementation(libs.timber)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Room Persistence Library
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
}

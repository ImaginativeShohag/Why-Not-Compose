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
    id(Libs.Android.library)
    kotlin("android")
    kotlin("kapt")
    id(Libs.Kotlin.composeCompilerGradlePlugin)
    id(Libs.Google.DevTools.ksp)
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

    composeCompiler {
        enableStrongSkippingMode = true
    }
}

dependencies {
    implementation(Libs.Kotlin.stdlib)

    // ----------------------------------------------------------------
    // Compose
    // ----------------------------------------------------------------
    implementation(platform(Libs.AndroidX.Compose.bom))

    implementation(Libs.AndroidX.Compose.ui)
    implementation(Libs.AndroidX.Compose.uiUtil)
    // Tooling support (Previews, etc.)
    debugImplementation(Libs.AndroidX.Compose.tooling)
    implementation(Libs.AndroidX.Compose.toolingPreview)
    // Animation
    implementation(Libs.AndroidX.Compose.animation)
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation(Libs.AndroidX.Compose.foundation)
    implementation(Libs.AndroidX.Compose.layout)
    // Material Design
    implementation(Libs.AndroidX.Compose.material)
    // Material design icons
    implementation(Libs.AndroidX.Compose.materialIconsCore)
    implementation(Libs.AndroidX.Compose.materialIconsExtended)
    // Integration with observables
    implementation(Libs.AndroidX.Compose.runtime)
    implementation(Libs.AndroidX.Compose.runtimeLivedata)
    implementation(Libs.AndroidX.Compose.runtimeTracing)
    // Compose Navigation Component
    implementation(Libs.AndroidX.Navigation.compose)
    // Constraint Layout
    implementation(Libs.AndroidX.ConstraintLayout.compose)
    // Integration with activities
    implementation(Libs.AndroidX.Activity.activityCompose)

    // Jetpack Compose Integration for ViewModel
    implementation(Libs.AndroidX.Lifecycle.viewModelCompose)

    // Paging
    implementation(Libs.AndroidX.Paging.compose)

    // Accompanist
    implementation(Libs.Accompanist.systemuicontroller)
    implementation(Libs.Accompanist.flowlayout)
    implementation(Libs.Accompanist.swipeRefresh)
    implementation(Libs.Accompanist.placeholder)

    // ----------------------------------------------------------------

    // Retrofit
    implementation(Libs.Square.Retrofit.core)
    implementation(Libs.Square.okhttpLoggingInterceptor)

    // Moshi
    implementation(Libs.Square.Retrofit.converterMoshi)
    implementation(Libs.Square.Moshi.core)
    ksp(Libs.Square.Moshi.codegen)

    // ViewModel and LiveData
    implementation(Libs.AndroidX.Lifecycle.viewModel)
    implementation(Libs.AndroidX.Lifecycle.livedata)
    implementation(Libs.AndroidX.Lifecycle.common)
    implementation(Libs.AndroidX.Lifecycle.runtime)

    // Timber
    implementation(Libs.timber)

    // Hilt
    implementation(Libs.Google.Hilt.core)
    kapt(Libs.Google.Hilt.compiler)
    implementation(Libs.AndroidX.Hilt.navigationCompose)

    // Room Persistence Library
    implementation(Libs.AndroidX.Room.runtime)
    ksp(Libs.AndroidX.Room.compiler)
}

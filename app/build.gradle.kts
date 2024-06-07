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
    id(Libs.Android.application)
    kotlin("android")
    kotlin("kapt")
    id(Libs.Kotlin.composeCompilerGradlePlugin)
    id(Libs.Google.DevTools.ksp)
    id(Libs.Kotlin.percelizeGradlePlugin)
    id(Libs.Google.Hilt.gradlePlugin)
    id(Libs.Google.Maps.secretsGradlePlugin)
    id(Libs.Google.Services.gradlePlugin)
    id(Libs.Google.Firebase.crashlyticsGradlePlugin)
}

android {
    namespace = "org.imaginativeworld.whynotcompose"
    compileSdk = BuildConfigConst.compileSdk

    defaultConfig {
        applicationId = "org.imaginativeworld.whynotcompose"
        minSdk = BuildConfigConst.minSdk
        targetSdk = BuildConfigConst.targetSdk
        versionCode = (findProperty("android.injected.version.code") as? String)?.toIntOrNull() ?: 1
        versionName = "6.0.0.${getCurrentDateAsYYMMDD()}" // Major.Minor.Patch.YYMMDD
        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        signingConfig = signingConfigs.getByName("debug")
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
        }
        named("debug") {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
        named("release") {
            isMinifyEnabled = false
            isShrinkResources = false
            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"

        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"

        // Enable experimental compose APIs
        freeCompilerArgs =
            freeCompilerArgs + "-opt-in=androidx.compose.material.ExperimentalMaterialApi"
        freeCompilerArgs =
            freeCompilerArgs + "-opt-in=androidx.compose.animation.ExperimentalAnimationApi"
        freeCompilerArgs =
            freeCompilerArgs + "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi"
        freeCompilerArgs =
            freeCompilerArgs + "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi"
        freeCompilerArgs =
            freeCompilerArgs + "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi"
    }

    buildFeatures {
        buildConfig = true
        compose = true

        // Disable unused AGP features
        viewBinding = false
        dataBinding = false

        // buildConfig false
        resValues = false
        shaders = false
    }

    composeCompiler {
        enableStrongSkippingMode = true
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("../debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }
}

dependencies {
    implementation(project(":base"))
    implementation(project(":common-ui-compose"))
    implementation(project(":tictactoe"))
    implementation(project(":exoplayer"))
    implementation(project(":cms"))

    implementation(Libs.Kotlin.stdlib)
    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appcompat)
    implementation(Libs.AndroidX.swipeRefreshLayout)

    // Let's not use material xml view components at all. :)
    // implementation(Google.android.material)

    testImplementation(Libs.junit)
    androidTestImplementation(Libs.AndroidX.Test.Ext.junit)
    androidTestImplementation(Libs.AndroidX.Test.espressoCore)
    androidTestImplementation(platform(Libs.AndroidX.Compose.bom))
    androidTestImplementation(Libs.AndroidX.Compose.uiTest)
    debugImplementation(Libs.AndroidX.Compose.uiTestManifest)

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
    implementation(Libs.AndroidX.Compose.material3)
    implementation(Libs.AndroidX.Compose.material3WindowSizeClass)
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

    // Splash Screen
    implementation(Libs.AndroidX.Core.splashScreen)

    // Retrofit
    implementation(Libs.Square.Retrofit.core)
    implementation(Libs.Square.okhttpLoggingInterceptor)

    // Moshi
    implementation(Libs.Square.Retrofit.converterMoshi)
    implementation(Libs.Square.Moshi.core)
    ksp(Libs.Square.Moshi.codegen)

    // Gson
    implementation(Libs.gson)

    // ViewModel and LiveData
    implementation(Libs.AndroidX.Lifecycle.viewModel)
    implementation(Libs.AndroidX.Lifecycle.livedata)
    implementation(Libs.AndroidX.Lifecycle.common)
    implementation(Libs.AndroidX.Lifecycle.runtime)

    // Kotlin Coroutines
    implementation(Libs.Coroutines.core)
    implementation(Libs.Coroutines.android)

    // Room Persistence Library
    implementation(Libs.AndroidX.Room.runtime)
    ksp(Libs.AndroidX.Room.compiler)

    // Room: Kotlin Extensions and Coroutines support for Room
    implementation(Libs.AndroidX.Room.ktx)

    // Coil
    implementation(Libs.Coil.compose)
    implementation(Libs.Coil.svg)

    // Paging
    implementation(Libs.AndroidX.Paging.runtime)

    // Timber
    implementation(Libs.timber)

    // Hilt
    implementation(Libs.Google.Hilt.core)
    kapt(Libs.Google.Hilt.compiler)
    implementation(Libs.AndroidX.Hilt.navigationCompose)

    // No Internet Library
    implementation(Libs.oopsNoInternet)

    // Maps
    implementation(Libs.Google.PlayService.maps)
    implementation(Libs.Google.Maps.core)
    implementation(Libs.Google.Maps.utils)
    implementation(Libs.Google.Maps.compose)

    // Lottie
    implementation(Libs.Airbnb.Lottie.compose)

    // uCrop
    implementation(Libs.Yalantis.uCrop)

    // OneSignal
    implementation(Libs.OneSignal.core)

    // Firebase
    implementation(platform(Libs.Google.Firebase.bom))

    implementation(Libs.Google.Firebase.analytics)
}

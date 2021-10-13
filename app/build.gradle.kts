import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "org.imaginativeworld.whynotcompose"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "0.0.0.000000" // Major.Minor.Patch.YYMMDD
        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

//        javaCompileOptions {
//            annotationProcessor(Options {
//                arguments += listOf(
//                    "room.schemaLocation"   = "$projectDir/schemas".toString(),
//                    "room.incremental"      = "true",
//                    "room.expandProjection" = "true"
//                )
//            })
//        }
    }

    buildTypes {
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
        freeCompilerArgs += "-Xallow-jvm-ir-dependencies"
        freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"

        // Enable experimental coroutines APIs, including Flow
        freeCompilerArgs += "-Xopt-in=kotlin.Experimental"

        // Enable experimental compose APIs
        freeCompilerArgs += "-Xopt-in=androidx.compose.material.ExperimentalMaterialApi"
        freeCompilerArgs += "-Xopt-in=androidx.compose.animation.ExperimentalAnimationApi"
        freeCompilerArgs += "-Xopt-in=androidx.compose.ui.ExperimentalComposeUiApi"
        freeCompilerArgs += "-Xopt-in=androidx.compose.foundation.ExperimentalFoundationApi"
    }

    buildFeatures {
        compose = true

        // Disable unused AGP features
        viewBinding = false
        dataBinding = false
        // buildConfig false
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Libs.AndroidX.Compose.version
    }
}

dependencies {
    implementation(Libs.Kotlin.stdlib)
    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appcompat)

    // Let"s not use material xml view components at all. :)
    // implementation("com.google.android.material:material:1.4.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation(Libs.AndroidX.Compose.uiTest)

    // ----------------------------------------------------------------
    // Compose
    // ----------------------------------------------------------------
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
    implementation(Libs.Accompanist.insets)
    implementation(Libs.Accompanist.systemuicontroller)
    implementation(Libs.Accompanist.flowlayout)
    implementation(Libs.Accompanist.pager)

    // ----------------------------------------------------------------

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    // Moshi
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.12.0")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.12.0")

    // Gson
    implementation("com.google.code.gson:gson:2.8.7")

    // ViewModel and LiveData
    implementation(Libs.AndroidX.Lifecycle.viewmodel)
    implementation(Libs.AndroidX.Lifecycle.livedata)
    implementation(Libs.AndroidX.Lifecycle.common)
    implementation(Libs.AndroidX.Lifecycle.runtime)

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")

    // Room Persistence Library
    implementation("androidx.room:room-runtime:2.3.0")
    kapt("androidx.room:room-compiler:2.3.0")

    // Room: Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.3.0")

    // Coil
    implementation("io.coil-kt:coil-compose:1.3.2")

    // Paging
    implementation(Libs.AndroidX.Paging.runtime)

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Hilt
    implementation(Libs.Hilt.core)
    kapt(Libs.Hilt.compiler)
    implementation(Libs.Hilt.navigationCompose)

    // No Internet Library
    implementation("org.imaginativeworld.oopsnointernet:oopsnointernet:2.0.0")

    // Maps
//    implementation("com.google.android.gms:play-services-maps:17.0.1")
    implementation("com.google.android.libraries.maps:maps:3.1.0-beta")
    implementation("com.google.maps.android:maps-v3-ktx:3.0.1")
}

kapt {
    correctErrorTypes = true
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
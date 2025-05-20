plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "org.imaginativeworld.whynotcompose.store"
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

        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"

        // Enable experimental compose APIs
        freeCompilerArgs =
            freeCompilerArgs + "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
        freeCompilerArgs =
            freeCompilerArgs + "-opt-in=androidx.compose.animation.ExperimentalAnimationApi"
        freeCompilerArgs =
            freeCompilerArgs + "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi"
        freeCompilerArgs =
            freeCompilerArgs + "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi"
    }

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {
    implementation(project(":base"))
    implementation(project(":common-ui-compose"))

    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.swiperefreshlayout)

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

    // Timber
    implementation(libs.timber)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Retrofit
    implementation(libs.retrofit.core)
    implementation(libs.okhttp.logging)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Moshi
    implementation(libs.retrofit.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)

    // Room Persistence Library
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    // Room: Kotlin Extensions and Coroutines support for Room
    implementation(libs.room.ktx)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)
    implementation(libs.coil.network)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Haze
    implementation(libs.haze)
    implementation(libs.haze.materials)

    implementation(libs.androidx.material3)
    implementation(libs.material)
}

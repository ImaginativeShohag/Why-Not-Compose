plugins {
    id(Libs.Android.library)
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "org.imaginativeworld.whynotcompose.cms"
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

        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"

        // Enable experimental coroutines APIs, including Flow
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.Experimental"

        // Enable experimental compose APIs
        freeCompilerArgs =
            freeCompilerArgs + "-Xopt-in=androidx.compose.material.ExperimentalMaterialApi"
        freeCompilerArgs =
            freeCompilerArgs + "-Xopt-in=androidx.compose.animation.ExperimentalAnimationApi"
        freeCompilerArgs =
            freeCompilerArgs + "-Xopt-in=androidx.compose.ui.ExperimentalComposeUiApi"
        freeCompilerArgs =
            freeCompilerArgs + "-Xopt-in=androidx.compose.foundation.ExperimentalFoundationApi"
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Libs.AndroidX.Compose.compilerVersion
    }
}

dependencies {
    implementation(project(":base"))
    implementation(project(":common-ui-compose"))

    implementation(Libs.Kotlin.stdlib)
    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appcompat)
    implementation(Libs.AndroidX.swipeRefreshLayout)

    // ----------------------------------------------------------------
    // Compose
    // ----------------------------------------------------------------
    implementation(platform(Libs.AndroidX.Compose.bom))

    implementation(Libs.AndroidX.Compose.compiler)
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
    implementation(Libs.Accompanist.systemuicontroller)
    implementation(Libs.Accompanist.flowlayout)
    implementation(Libs.Accompanist.pager)
    implementation(Libs.Accompanist.swipeRefresh)
    implementation(Libs.Accompanist.placeholder)

    // ----------------------------------------------------------------

    // Timber
    implementation(Libs.timber)

    // Hilt
    implementation(Libs.Google.Hilt.core)
    kapt(Libs.Google.Hilt.compiler)
    implementation(Libs.AndroidX.Hilt.navigationCompose)

    // Retrofit
    implementation(Libs.Square.Retrofit.core)
    implementation(Libs.Square.okhttpLoggingInterceptor)

    // Moshi
    implementation(Libs.Square.Retrofit.converterMoshi)
    implementation(Libs.Square.Moshi.core)
    kapt(Libs.Square.Moshi.codegen)

    // Room Persistence Library
    implementation(Libs.AndroidX.Room.runtime)
    kapt(Libs.AndroidX.Room.compiler)

    // Room: Kotlin Extensions and Coroutines support for Room
    implementation(Libs.AndroidX.Room.ktx)
}

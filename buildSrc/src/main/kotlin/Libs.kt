/*
 * Copyright 2021 Md. Mahmudul Hasan Shohag
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

object Libs {
    const val jdkDesugar = "com.android.tools:desugar_jdk_libs:1.1.5"

    const val junit = "junit:junit:4.13.2"

    const val material = "com.google.android.material:material:1.3.0"

    const val gson = "com.google.code.gson:gson:2.9.0"

    const val timber = "com.jakewharton.timber:timber:5.0.1"

    const val oopsNoInternet = "org.imaginativeworld.oopsnointernet:oopsnointernet:2.0.0"

    object Android {
        const val application = "com.android.application"
        const val library = "com.android.library"
    }

    object Coil {
        private const val version = "2.5.0"

        const val compose = "io.coil-kt:coil-compose:$version"
        const val svg = "io.coil-kt:coil-svg:$version"
    }

    object DiffPlug {
        const val version = "6.23.2"

        const val spotless = "com.diffplug.spotless"
    }

    object Gradle {
        const val version = "8.2.0"
    }

    object Yalantis {
        const val uCrop = "com.github.yalantis:ucrop:2.2.8"
    }

    object Airbnb {
        object Lottie {
            const val compose = "com.airbnb.android:lottie-compose:6.2.0"
        }
    }

    object Google {
        const val service = "com.google.gms:google-services:4.3.15"

        const val exoplayer = "com.google.android.exoplayer:exoplayer:2.19.1"

        object DevTools {
            const val kspVersion = "1.9.20-1.0.14"

            const val ksp = "com.google.devtools.ksp"
        }

        object Firebase {
            const val crashlyticsGradlePluginVersion = "2.9.9"

            const val bom = "com.google.firebase:firebase-bom:32.6.0"
            const val analytics = "com.google.firebase:firebase-analytics-ktx"

            const val crashlyticsGradlePlugin = "com.google.firebase.crashlytics"
        }

        object Hilt {
            const val version = "2.49"

            const val core = "com.google.dagger:hilt-android:$version"
            const val compiler = "com.google.dagger:hilt-compiler:$version"

            const val gradlePlugin = "com.google.dagger.hilt.android"
        }

        object PlayService {
            const val maps = "com.google.android.gms:play-services-maps:18.2.0"
        }

        object Maps {
            private const val version = "5.0.0"
            const val secretsGradlePluginVersion = "2.0.1"

            const val core = "com.google.maps.android:maps-ktx:$version"
            const val utils = "com.google.maps.android:maps-utils-ktx:$version"
            const val compose = "com.google.maps.android:maps-compose:4.3.0"
            const val secretsGradlePlugin =
                "com.google.android.libraries.mapsplatform.secrets-gradle-plugin"
        }

        object Services {
            const val version = "4.4.0"

            const val gradlePlugin = "com.google.gms.google-services"
        }
    }

    object Accompanist {
        private const val version = "0.32.0"

        const val systemuicontroller =
            "com.google.accompanist:accompanist-systemuicontroller:$version"
        const val flowlayout = "com.google.accompanist:accompanist-flowlayout:$version"
        const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:$version"
        const val placeholder = "com.google.accompanist:accompanist-placeholder-material:$version"
    }

    object Kotlin {
        const val version = "1.9.20"

        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version"

        const val percelizeGradlePlugin = "kotlin-parcelize"
    }

    object Coroutines {
        private const val version = "1.7.3"

        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.6.1"
        const val coreKtx = "androidx.core:core-ktx:1.12.0"
        const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.8.1"
        }

        object Compose {
            private const val bomVersion = "2023.10.01"
            const val compilerVersion = "1.5.5"
            private const val runtimeTracingVersion = "1.0.0-alpha04"

            // TODO: Remove when library gets stable.
            private const val material3Version = "1.2.0-alpha12"

            const val bom = "androidx.compose:compose-bom:$bomVersion"

            const val compiler = "androidx.compose.compiler:compiler:$compilerVersion"

            const val foundation = "androidx.compose.foundation:foundation"
            const val layout = "androidx.compose.foundation:foundation-layout"

            const val material = "androidx.compose.material:material"
            const val materialIconsCore = "androidx.compose.material:material-icons-core"
            const val materialIconsExtended =
                "androidx.compose.material:material-icons-extended"

            const val material3 = "androidx.compose.material3:material3:$material3Version"
            const val material3WindowSizeClass =
                "androidx.compose.material3:material3-window-size-class:$material3Version"

            const val runtime = "androidx.compose.runtime:runtime"
            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata"
            const val runtimeTracing =
                "androidx.compose.runtime:runtime-tracing:$runtimeTracingVersion"

            const val ui = "androidx.compose.ui:ui"
            const val uiUtil = "androidx.compose.ui:ui-util"

            const val tooling = "androidx.compose.ui:ui-tooling"
            const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:1.6.0-beta01"

            const val viewBinding = "androidx.compose.ui:ui-viewbinding"
            const val animation = "androidx.compose.animation:animation"

            const val test = "androidx.compose.ui:ui-test"
            const val uiTest = "androidx.compose.ui:ui-test-junit4"
            const val uiTestManifest = "androidx.compose.ui:ui-test-manifest"
        }

        object Core {
            const val splashScreen = "androidx.core:core-splashscreen:1.0.1"
        }

        object ConstraintLayout {
            private const val version = "1.0.1"

            const val compose = "androidx.constraintlayout:constraintlayout-compose:$version"
        }

        object Hilt {
            const val navigationCompose = "androidx.hilt:hilt-navigation-compose:1.1.0"
        }

        object Lifecycle {
            private const val version = "2.6.2"

            const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val common = "androidx.lifecycle:lifecycle-common:$version"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
        }

        object Navigation {
            private const val version = "2.7.5"

            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
            const val compose = "androidx.navigation:navigation-compose:$version"
        }

        object Paging {
            private const val version = "3.2.1"

            const val runtime = "androidx.paging:paging-runtime:$version"
            const val compose = "androidx.paging:paging-compose:$version"
        }

        object Room {
            private const val version = "2.6.1"

            const val runtime = "androidx.room:room-runtime:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            const val ktx = "androidx.room:room-ktx:$version"
        }

        object Test {
            private const val version = "1.4.0"

            const val core = "androidx.test:core:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.5"

                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

            const val espressoCore = "androidx.test.espresso:espresso-core:3.5.1"
        }
    }

    object OneSignal {
        const val core = "com.onesignal:OneSignal:4.8.6"
    }

    object Square {
        object Retrofit {
            private const val version = "2.9.0"

            const val core = "com.squareup.retrofit2:retrofit:$version"
            const val converterMoshi = "com.squareup.retrofit2:converter-moshi:$version"
        }

        const val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.3"

        object Moshi {
            private const val version = "1.15.0"

            const val core = "com.squareup.moshi:moshi-kotlin:$version"
            const val codegen = "com.squareup.moshi:moshi-kotlin-codegen:$version"
        }
    }
}

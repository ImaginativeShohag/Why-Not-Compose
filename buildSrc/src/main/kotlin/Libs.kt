object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.0.4"

    const val secretsGradlePlugin =
        "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.0"

    const val jdkDesugar = "com.android.tools:desugar_jdk_libs:1.1.5"

    const val junit = "junit:junit:4.13.2"

    const val material = "com.google.android.material:material:1.3.0"

    const val gson = "com.google.code.gson:gson:2.8.9"

    const val timber = "com.jakewharton.timber:timber:5.0.1"

    const val oopsNoInternet = "org.imaginativeworld.oopsnointernet:oopsnointernet:2.0.0"

    object Coil {
        private const val version = "1.4.0"

        const val compose = "io.coil-kt:coil-compose:$version"
        const val svg = "io.coil-kt:coil-svg:$version"
    }

    object Yalantis {
        const val uCrop = "com.github.yalantis:ucrop:2.2.7"
    }

    object Airbnb {
        object Lottie {
            const val compose = "com.airbnb.android:lottie-compose:4.2.2"
        }
    }

    object Google {
        object PlayService {
            const val maps = "com.google.android.gms:play-services-maps:18.0.1"
        }

        object Maps {
            private const val version = "3.3.0"

            const val core = "com.google.maps.android:maps-ktx:$version"
            const val utils = "com.google.maps.android:maps-utils-ktx:$version"
        }
    }

    object Accompanist {
        private const val version = "0.21.4-beta"

        const val insets = "com.google.accompanist:accompanist-insets:$version"
        const val systemuicontroller =
            "com.google.accompanist:accompanist-systemuicontroller:$version"
        const val flowlayout = "com.google.accompanist:accompanist-flowlayout:$version"
        const val pager = "com.google.accompanist:accompanist-pager:$version"
        const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:$version"
        const val placeholder = "com.google.accompanist:accompanist-placeholder-material:$version"
    }

    object Kotlin {
        private const val version = "1.6.0"

        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Coroutines {
        private const val version = "1.5.2"

        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object Hilt {
        private const val version = "2.40.5"

        const val androidGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val core = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-compiler:$version"
        const val navigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0-beta01"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.4.0"
        const val coreKtx = "androidx.core:core-ktx:1.7.0"
        const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.4.0"
        }

        object Compose {
            const val version = "1.1.0-beta04"

            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val layout = "androidx.compose.foundation:foundation-layout:$version"

            const val material = "androidx.compose.material:material:$version"
            const val materialIconsCore = "androidx.compose.material:material-icons-core:$version"
            const val materialIconsExtended =
                "androidx.compose.material:material-icons-extended:$version"

            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:$version"

            const val ui = "androidx.compose.ui:ui:$version"
            const val uiUtil = "androidx.compose.ui:ui-util:$version"

            const val tooling = "androidx.compose.ui:ui-tooling:$version"
            const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:$version"

            const val viewBinding = "androidx.compose.ui:ui-viewbinding:$version"
            const val animation = "androidx.compose.animation:animation:$version"

            const val test = "androidx.compose.ui:ui-test:$version"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:$version"
            const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:$version"
        }

        object ConstraintLayout {
            private const val version = "1.0.0-rc02"

            const val compose = "androidx.constraintlayout:constraintlayout-compose:$version"
        }

        object Lifecycle {
            private const val version = "2.4.0"

            const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val common = "androidx.lifecycle:lifecycle-common:$version"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
        }

        object Navigation {
            private const val version = "2.4.0-beta02"

            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
            const val compose = "androidx.navigation:navigation-compose:$version"
        }

        object Paging {
            private const val version = "3.1.0"
            private const val composeVersion = "1.0.0-alpha14"

            const val runtime = "androidx.paging:paging-runtime-ktx:$version"
            const val compose = "androidx.paging:paging-compose:$composeVersion"
        }

        object Room {
            private const val version = "2.3.0"

            const val runtime = "androidx.room:room-runtime:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            const val ktx = "androidx.room:room-ktx:$version"
        }

        object Test {
            private const val version = "1.4.0"

            const val core = "androidx.test:core:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.3"

                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

            const val espressoCore = "androidx.test.espresso:espresso-core:3.4.0"
        }
    }

    object Square {
        object Retrofit {
            private const val version = "2.9.0"

            const val core = "com.squareup.retrofit2:retrofit:$version"
            const val converterMoshi = "com.squareup.retrofit2:converter-moshi:$version"
        }

        const val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.1"

        object Moshi {
            private const val version = "1.13.0"

            const val core = "com.squareup.moshi:moshi-kotlin:$version"
            const val codegen = "com.squareup.moshi:moshi-kotlin-codegen:$version"
        }
    }
}

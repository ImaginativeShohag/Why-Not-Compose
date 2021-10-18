object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:_"

    const val secretsGradlePlugin =
        "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:_"

    const val jdkDesugar = "com.android.tools:desugar_jdk_libs:_"

    const val junit = "junit:junit:_"

    const val material = "com.google.android.material:material:_"

    const val gson = "com.google.code.gson:gson:_"

    const val coil = "io.coil-kt:coil-compose:_"

    const val timber = "com.jakewharton.timber:timber:_"

    const val oopsNoInternet = "org.imaginativeworld.oopsnointernet:oopsnointernet:_"

    object Google {
        object PlayService {
            const val maps = "com.google.android.gms:play-services-maps:_"
        }

        object Maps {
            private const val version = "_"

            const val core = "com.google.maps.android:maps-ktx:_"
            const val utils = "com.google.maps.android:maps-utils-ktx:_"
        }
    }

    object Accompanist {
        private const val version = "_"
        const val insets = "com.google.accompanist:accompanist-insets:_"
        const val systemuicontroller =
            "com.google.accompanist:accompanist-systemuicontroller:_"
        const val flowlayout = "com.google.accompanist:accompanist-flowlayout:_"
        const val pager = "com.google.accompanist:accompanist-pager:_"
    }

    object Kotlin {
        private const val version = "_"

        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:_"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:_"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:_"
    }

    object Coroutines {
        private const val version = "_"

        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:_"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:_"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:_"
    }

    object Hilt {
        private const val version = "_"

        const val androidGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:_"
        const val core = "com.google.dagger:hilt-android:_"
        const val compiler = "com.google.dagger:hilt-compiler:_"
        const val navigationCompose = "androidx.hilt:hilt-navigation-compose:_"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:_"
        const val coreKtx = "androidx.core:core-ktx:_"

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:_"
        }

        object Compose {
            const val snapshot = ""
            const val version = "_"

            const val foundation = "androidx.compose.foundation:foundation:_"
            const val layout = "androidx.compose.foundation:foundation-layout:_"
            const val material = "androidx.compose.material:material:_"
            const val materialIconsCore = "androidx.compose.material:material-icons-core:_"
            const val materialIconsExtended =
                "androidx.compose.material:material-icons-extended:_"
            const val runtime = "androidx.compose.runtime:runtime:_"
            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:_"
            const val ui = "androidx.compose.ui:ui:_"
            const val tooling = "androidx.compose.ui:ui-tooling:_"
            const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:_"
            const val test = "androidx.compose.ui:ui-test:_"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:_"
            const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:_"
            const val uiUtil = "androidx.compose.ui:ui-util:_"
            const val viewBinding = "androidx.compose.ui:ui-viewbinding:_"
            const val animation = "androidx.compose.animation:animation:_"
        }

        object ConstraintLayout {
            private const val version = "_"

            const val compose = "androidx.constraintlayout:constraintlayout-compose:_"
        }

        object Paging {
            private const val version = "_"
            private const val composeVersion = "_"

            const val runtime = "androidx.paging:paging-runtime-ktx:_"
            const val compose = "androidx.paging:paging-compose:_"
        }

        object Navigation {
            private const val version = "_"

            const val fragment = "androidx.navigation:navigation-fragment-ktx:_"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:_"
            const val compose = "androidx.navigation:navigation-compose:_"
        }

        object Lifecycle {
            private const val version = "_"

            const val extensions = "androidx.lifecycle:lifecycle-extensions:_"
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:_"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:_"
            const val common = "androidx.lifecycle:lifecycle-common:_"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:_"
            const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:_"
        }

        object Room {
            private const val version = "_"

            const val runtime = "androidx.room:room-runtime:_"
            const val compiler = "androidx.room:room-compiler:_"
            const val ktx = "androidx.room:room-ktx:_"

        }

        object Test {
            private const val version = "_"

            const val core = "androidx.test:core:_"
            const val rules = "androidx.test:rules:_"

            object Ext {
                private const val version = "_"

                const val junit = "androidx.test.ext:junit-ktx:_"
            }

            const val espressoCore = "androidx.test.espresso:espresso-core:_"
        }
    }

    object Square {
        object Retrofit {
            private const val version = "_"

            const val core = "com.squareup.retrofit2:retrofit:_"
            const val converterMoshi = "com.squareup.retrofit2:converter-moshi:_"
        }

        const val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:_"

        object Moshi {
            private const val version = "_"

            const val core = "com.squareup.moshi:moshi-kotlin:_"
            const val codegen = "com.squareup.moshi:moshi-kotlin-codegen:_"
        }
    }
}

object Urls {
    const val composeSnapshotRepo = "https://androidx.dev/snapshots/builds/" +
            "${Libs.AndroidX.Compose.snapshot}/artifacts/repository/"
    const val accompanistSnapshotRepo = "https://oss.sonatype.org/content/repositories/snapshots"
}
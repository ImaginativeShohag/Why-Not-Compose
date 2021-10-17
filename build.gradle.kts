// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Libs.androidGradlePlugin)
        classpath(Libs.Kotlin.gradlePlugin)

        classpath(Libs.Hilt.androidGradlePlugin)

        classpath(Libs.secretsGradlePlugin)
    }
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
}
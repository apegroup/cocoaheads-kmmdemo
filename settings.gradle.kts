rootProject.name = "CocoaHeads"
include(":composeApp")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("multiplatform").version("1.8.20-RC")
        kotlin("plugin.serialization").version("1.8.20-RC")
        id("com.android.library").version("7.4.2")
        id("com.rickclephas.kmp.nativecoroutines").version("1.0.0-ALPHA-6")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

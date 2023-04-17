plugins {
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.ksp)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.multiplatform)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.compose)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.cocoapods)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.android.application)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.libres)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.buildConfig)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.kotlinx.serialization)
    @Suppress("DSL_SCOPE_VIOLATION")
    id("com.rickclephas.kmp.nativecoroutines")
}

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Compose application framework"
        homepage = "empty"
        ios.deploymentTarget = "15.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }

        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(libs.libres)
                implementation(libs.composeImageLoader)
                implementation(libs.napier)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.ktor.core)
                implementation(libs.ktor.serialization)
                implementation(libs.ktor.logging)
                implementation(libs.ktor.content.negotiation)
                implementation(libs.composeIcons.featherIcons)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.datetime)
                implementation(libs.multiplatformSettings)
                implementation(libs.koin.core)
                implementation(libs.kstore)
                implementation(libs.touchlab.kermit)
                implementation("com.rickclephas.kmm:kmm-viewmodel-core:1.0.0-ALPHA-6")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.appcompat)
                implementation(libs.androidx.activityCompose)
                implementation(libs.compose.uitooling)
                implementation(libs.kotlinx.coroutines.android)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.koin.core)
                implementation(libs.koin.android)
                implementation(libs.koin.compose)
                implementation(libs.androidx.lifecycle.viewmodel.ktx)
            }
        }

        val iosMain by creating {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        /* Main hierarchy */
        androidMain.dependsOn(commonMain)
        iosMain.dependsOn(commonMain)
        iosX64Main.dependsOn(iosMain)
        iosArm64Main.dependsOn(iosMain)
        iosSimulatorArm64Main.dependsOn(iosMain)

        /* Test source sets */
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidUnitTest by getting
        val iosTest by creating
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting

        /* Test hierarchy */
        androidUnitTest.dependsOn(commonTest)
        iosTest.dependsOn(commonTest)
        iosX64Test.dependsOn(iosTest)
        iosArm64Test.dependsOn(iosTest)
        iosSimulatorArm64Test.dependsOn(iosTest)
    }
}

android {
    namespace = "com.umain.cocoaheads"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        applicationId = "com.umain.cocoaheads.androidApp"
        versionCode = 1
        versionName = "1.0.0"
    }
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/resources")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }
    packagingOptions {
        resources.excludes.add("META-INF/**")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xuse-experimental=kotlin.time.ExperimentalTime")
    }
}

libres {
    // https://github.com/Skeptick/libres#setup
}

buildConfig {
    // BuildConfig configuration here.
    // https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts
}

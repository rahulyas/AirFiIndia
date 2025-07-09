plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("plugin.serialization") version "2.0.0"
    alias(libs.plugins.devToolsKspGoogle)
    alias(libs.plugins.daggerHiltAndroid)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.rahul.airfiindia"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.rahul.airfiindia"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }

    packaging {
        resources {
            excludes += listOf("/META-INF/{AL2.0,LGPL2.1}",
                "META-INF/versions/9/OSGI-INF/MANIFEST.MF")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.rules)
    implementation(libs.core)
    implementation(libs.androidx.ui.android)
    implementation(libs.androidx.ui.graphics.android)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)
    implementation(libs.androidx.games.activity)
    implementation(libs.androidx.compose.testing)
    implementation(libs.androidx.room.common.jvm)
    implementation(libs.play.services.auth)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.bundles.testing)
    implementation(libs.accompanist.permissions)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.accompanist.insets)
    //kotlinx serialization
    implementation(libs.kotlinx.serialization.json)
    implementation( libs.androidx.lifecycle.runtime.compose)
    //dagger hilt
    ksp(libs.com.google.dagger.hilt.compiler)
    implementation(libs.bundles.daggerHilt)
    //preferences data store
    implementation(libs.androidx.datastore.preferences)


    implementation( libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation (libs.androidx.room.ktx)

    //implementation(libs.ui.graphics) // For RenderEffect

    //Coil supports GIFs
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)
    implementation(libs.coil.svg) // or match your Coil version

    //testcase
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)

}
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //Dagger hilt
    kotlin("kapt")
    id("org.jetbrains.dokka") version "1.9.20"
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.notehub"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.notehub"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    composeOptions.kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {
    //Firebase
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.storage.ktx)
    //google auth
    implementation(libs.bundles.google.credentials)
    //DataStore
    implementation(libs.androidx.datastore.preferences)
    //Dagger
    implementation(libs.bundles.dagger.hilt)
    implementation(libs.firebase.storage)
    kapt(libs.bundles.dagger.compiler)
    //Documentation
    dokkaPlugin(libs.android.documentation.plugin)
    //Markdown
    implementation(project(":twain"))
    //Other
    implementation(libs.bundles.compose.ui)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.core)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

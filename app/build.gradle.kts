plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //Dagger hilt
    kotlin("kapt")
    id("org.jetbrains.dokka") version "1.9.20"
    id("com.google.dagger.hilt.android")
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
    //navigation
    implementation ("androidx.navigation:navigation-compose:2.7.7")
    //google auth
    implementation("androidx.credentials:credentials:1.2.2")
    implementation("androidx.credentials:credentials-play-services-auth:1.2.2")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.0")
    //DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    //Markdown
    implementation(project(":twain"))
    //Dagger hilt
    kapt("com.google.dagger:hilt-android-compiler:2.47")
    implementation("com.google.dagger:hilt-android:2.47")
    implementation("com.google.dagger:dagger:2.47")
    kapt("com.google.dagger:dagger-compiler:2.47")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    //Documentation
    dokkaPlugin("org.jetbrains.dokka:android-documentation-plugin:1.9.20")
    //Other
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation(libs.androidx.activity.compose)
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.core)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

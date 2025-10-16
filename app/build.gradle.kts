import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias (libs.plugins.parcelizeKotlinAndroid )
    alias (libs.plugins.hilt)
    alias (libs.plugins.kapt)
}

android {
    namespace = "ar.edu.unicen.seminario"
    compileSdk = 36

    defaultConfig {
        applicationId = "ar.edu.unicen.seminario"
        minSdk = 24
        targetSdk = 36
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

    buildFeatures{
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.play.services.maps)
    implementation(libs.androidx.activityKtx)
    implementation(libs.hilt.android)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.google.material)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.glide)
    implementation(libs.paging)
    kapt(libs.glide.compiler)
    kapt(libs.hilt.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}
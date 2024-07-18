import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("androidx.navigation.safeargs.kotlin") version "2.7.7"
}

android {

    val file = rootProject.file("local.properties")
    val properties = Properties()
    properties.load(FileInputStream(file))
    namespace = "com.example.converter"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.converter"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val credentialsProperties = Properties().apply {
            load(FileInputStream(File(rootProject.rootDir, "credentials.properties")))
        }

        buildConfigField(
            "String",
            "API_KEY",
            "${credentialsProperties.getProperty("API_KEY")}"
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    buildFeatures{
        viewBinding=true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}


    dependencies {
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        implementation(libs.androidx.activity)
        implementation(libs.androidx.constraintlayout)
        implementation(libs.androidx.legacy.support.v4)
        implementation(libs.androidx.lifecycle.livedata.ktx)
        implementation(libs.androidx.lifecycle.viewmodel.ktx)
        implementation(libs.androidx.fragment.ktx)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        implementation(libs.androidx.lifecycle.viewmodel.ktx.v240)
        implementation(libs.androidx.lifecycle.livedata.ktx.v240)
        implementation(libs.androidx.fragment.ktx)
        implementation(libs.retrofit)
        implementation(libs.converter.gson)
        implementation(libs.kotlinx.coroutines.android)
        implementation(libs.glide)
        implementation(platform(libs.okhttp.bom))
        implementation(libs.logging.interceptor)
        implementation(libs.kotlinx.coroutines.android.v152)
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.material.v130)
    }
}
dependencies {
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
}

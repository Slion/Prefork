/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "androidx.preference"
    compileSdk = 36

    defaultConfig {
        minSdk = 21
        targetSdk = 36
        vectorDrawables.useSupportLibrary = true
        consumerProguardFiles("proguard-rules.pro")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
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

    lint {
        abortOnError = false
        disable += "RestrictedApi"
    }
}

dependencies {
    api("org.jspecify:jspecify:1.0.0")
    api("androidx.annotation:annotation:1.8.1")
    api("androidx.appcompat:appcompat:1.7.0")
    api("androidx.core:core:1.13.0")
    implementation("androidx.collection:collection:1.4.2")
    api("androidx.activity:activity-ktx:1.8.0")
    api("androidx.fragment:fragment-ktx:1.6.0")
    api("androidx.recyclerview:recyclerview:1.3.0")
    api("androidx.slidingpanelayout:slidingpanelayout:1.2.0")

    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test:core:1.6.1")
    androidTestImplementation("androidx.test:runner:1.6.2")
    androidTestImplementation("androidx.test:rules:1.6.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("org.mockito:mockito-core:5.+")
    androidTestImplementation("com.linkedin.dexmaker:dexmaker-mockito:2.28.3")
}

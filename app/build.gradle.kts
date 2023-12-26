plugins {
    id("com.android.application")
}

android {
    namespace = "com.tienminh.a5s_project_hand_on"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.tienminh.a5s_project_hand_on"
        minSdk = 26
        targetSdk = 33
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("org.apache.poi:poi:5.2.3")
    implementation("org.apache.poi:poi-ooxml:5.2.3")
//    implementation("com.github.BoardiesITSolutions:Android-MySQL-Connector:0.23")
//    implementation("mysql:mysql-connector-java:8.0.23")
//    implementation("mysql:mysql-connector-java:5.1.23")
//    implementation("com.github.BoardiesITSolutions:Android-MySQL-Connector:0.52_MySQL8")
    //implementation("androix.cardview:cardview:1.0.0") // thêm thư viện cardview
}
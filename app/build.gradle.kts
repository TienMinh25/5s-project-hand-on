plugins {
    id("com.android.application")
}

android {
    namespace = "com.tienminh.a5s_project_hand_on"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.tienminh.a5s_project_hand_on"
        minSdk = 24
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

    packagingOptions {
        exclude("org/apache/batik/**")
        exclude("META-INF/**")
        exclude("license/**")
        exclude("org/apache/xmlgraphics/**")
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("org.apache.poi:poi:5.0.0")
    implementation("org.apache.poi:poi-ooxml:5.0.0")
    implementation("org.apache.xmlgraphics:batik-all:1.13") {
        exclude(group = "org.apache.xmlgraphics", module = "batik-svgbrowser:1.13")
        exclude(group = "org.apache.xmlgraphics", module = "batik-util:1.13")
        exclude(group = "org.apache.xmlgraphics", module = "batik-xml:1.13")
    }
    implementation("org.apache.xmlbeans:xmlbeans:4.0.0")
//    implementation("com.github.BoardiesITSolutions:Android-MySQL-Connector:0.23")
//    implementation("mysql:mysql-connector-java:8.0.23")
//    implementation("mysql:mysql-connector-java:5.1.23")
//    implementation("com.github.BoardiesITSolutions:Android-MySQL-Connector:0.52_MySQL8")
    //implementation("androix.cardview:cardview:1.0.0") // thêm thư viện cardview
}
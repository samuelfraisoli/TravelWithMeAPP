plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    //google services (firebase)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.travelwithmeapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.travelwithmeapp"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    viewBinding {
        enable = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.8.0")
    implementation("com.google.firebase:firebase-auth-ktx:22.3.1")
    implementation("com.google.android.gms:play-services-auth:21.0.0")
    implementation("com.google.firebase:firebase-firestore-ktx:24.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //grafico navegación
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")

    //firebase BoM
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))

    //google analytics
    implementation("com.google.firebase:firebase-analytics")

    //firebase auth -> autenticacion firebase
    implementation ("com.firebaseui:firebase-ui-auth:7.2.0")

    //firebase firestore -> bbdd firebase
    implementation("com.google.firebase:firebase-firestore")

    //carousel material design3
    implementation("io.coil-kt:coil:2.5.0")

    //volley
    implementation ("com.android.volley:volley:1.2.1")

    //gson
    implementation ("com.google.code.gson:gson:2.8.8")










}
plugins {
    `kotlin-dsl`
}

repositories {
    google()
    jcenter()
}

dependencies {
    val android =   "3.6.0-alpha03" // Updated: Jun 06, 2019
    val dokka =     "0.9.18"        // Updated: Mar 19, 2019
    val bintray =   "1.8.4"         // Updated: Jul 08, 2018

    implementation( "com.android.tools.build:gradle:$android" )
    implementation( "org.jetbrains.dokka:dokka-gradle-plugin:$dokka" )
    implementation( "org.jetbrains.dokka:dokka-android-gradle-plugin:$dokka" )
    implementation( "com.jfrog.bintray.gradle:gradle-bintray-plugin:$bintray" )
}

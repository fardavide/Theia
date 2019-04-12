@file:Suppress("MayBeConstant")

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.ScriptHandlerScope
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.maven

val repos: RepositoryHandler.() -> Unit get() = {
    google()
    jcenter()
    maven("https://kotlin.bintray.com/kotlinx" )
}

val ScriptHandlerScope.classpathDependencies: DependencyHandlerScope.() -> Unit get() = {
    classpath( kotlin("gradle-plugin", Versions.kotlin ) )
    classpath( Libs.Android.gradle_plugin )
    classpath( Libs.Publishing.bintray_plugin )
    classpath( Libs.Publishing.maven_plugin )
}

@Suppress("unused")
fun DependencyHandler.applyAndroidTests() {
    val unit = "testImplementation"
    val android = "androidTestImplementation"
    add( unit, Libs.test );                 add( android, Libs.test )
    add( unit, Libs.test_junit );           add( android, Libs.test_junit )
    add( unit, Libs.Android.lifecycle );    add( android, Libs.Android.lifecycle )
    add( unit, Libs.mockk );                add( android, Libs.mockk )
    // add( unit, Libs.Android.robolectric )
    add( android, Libs.Android.espresso )
    add( android, Libs.mockk_android )
    add( android, Libs.Android.test_runner )
}

object Versions {
    val kotlin =                        "1.3.21"
    val coroutines =                    "1.1.1"
    val mockk =                         "1.9"
    val ktor =                          "1.1.3"

    val android_constraint_layout =     "2.0.0-alpha1"
    val android_espresso =              "3.1.1"
    val android_gradle_plugin =         "3.3.1"
    val android_ktx =                   "1.1.0-alpha03"
    val android_lifecycle =             "2.0.0"
    val android_recycler_view =         "1.0.0"
    val android_robolectric =           "4.2"
    val android_support =               "1.1.0-alpha02"
    val android_test_runner =           "1.1.1"

    val publishing_bintray_plugin =     "1.8.4"
    val publishing_maven_plugin =       "2.1"
}

@Suppress("unused")
object Libs {

    /* Kotlin */
    val kotlin =                                "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val coroutines_android =                    "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    val kotlin_reflect =                        "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    val test =                                  "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
    val test_junit =                            "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"

    val ktor =                                  "io.ktor:ktor-client-core:${Versions.ktor}"
    val ktor_apache =                           "io.ktor:ktor-client-apache:${Versions.ktor}"
    val ktor_android =                          "io.ktor:ktor-client-android:${Versions.ktor}"
    val mockk =                                 "io.mockk:mockk:${Versions.mockk}"
    val mockk_android =                         "io.mockk:mockk-android:${Versions.mockk}"

    /* Android */
    object Android {
        val appcompat =                         "androidx.appcompat:appcompat:${Versions.android_support}"
        val constraint_layout =                 "androidx.constraintlayout:constraintlayout:${Versions.android_constraint_layout}"
        val espresso =                          "androidx.test.espresso:espresso-core:${Versions.android_espresso}"
        val gradle_plugin =                     "com.android.tools.build:gradle:${Versions.android_gradle_plugin}"
        val ktx =                               "androidx.core:core-ktx:${Versions.android_ktx}"
        val lifecycle =                         "androidx.lifecycle:lifecycle-runtime:${Versions.android_lifecycle}"
        val lifecycle_jdk8 =                    "androidx.lifecycle:lifecycle-common-java8:${Versions.android_lifecycle}"
        val recycler_view =                     "androidx.recyclerview:recyclerview:${Versions.android_recycler_view}"
        val robolectric =                       "org.robolectric:robolectric:${Versions.android_robolectric}"
        val support_annotations =               "com.android.support:support-annotations:28.0.0"
        val test_runner =                       "com.android.support.test:runner:${Versions.android_test_runner}"
    }

    /* Publishing */
    object Publishing {
        val bintray_plugin =                    "com.jfrog.bintray.gradle:gradle-bintray-plugin:${Versions.publishing_bintray_plugin}"
        val maven_plugin =                      "com.github.dcendents:android-maven-gradle-plugin:${Versions.publishing_maven_plugin}"
    }
}
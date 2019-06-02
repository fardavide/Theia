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
    classpath( kotlin("gradle-plugin", Version.kotlin ) )
    classpath( Lib.Android.gradle_plugin )
    classpath( Lib.Publishing.dokka_plugin )
    classpath( Lib.Publishing.bintray_plugin )
    classpath( Lib.Publishing.maven_plugin )
}

@Suppress("unused")
fun DependencyHandler.applyAndroidTests() {
    val unit = "testImplementation"
    val android = "androidTestImplementation"
    add( unit, Lib.test );                 add( android, Lib.test )
    add( unit, Lib.test_junit );           add( android, Lib.test_junit )
    add( unit, Lib.Android.lifecycle );    add( android, Lib.Android.lifecycle )
    add( unit, Lib.mockk );                add( android, Lib.mockk )
    // add( unit, Lib.Android.robolectric )
    add( android, Lib.Android.espresso )
    add( android, Lib.mockk_android )
    add( android, Lib.Android.test_runner )
}

object Version {

    /* Kotlin */
    const val kotlin =                          "1.3.31"        // Updated: Apr 25, 2019
    const val coroutines =                      "1.2.1"         // Updated: Apr 25, 2019

    /* Other */
    const val ktor =                            "1.2.1"         // Updated:
    const val mockk =                           "1.9.3"         // Updated: Mar 25, 2019

    /* Android */
    const val android_constraintLayout =        "2.0.0-beta1"   // Updated: May 8, 2019
    const val android_espresso =                "3.2.0"         // Updated: May 30, 2019
    const val android_gradle_plugin =           "3.5.0-beta03"  // Updated: May 28, 2019
    const val android_ktx =                     "1.1.0-beta01"  // Updated: May 8, 2019
    const val android_lifecycle =               "2.1.0-beta01"  // Updated: May 8, 2019
    const val android_recyclerView =            "1.1.0-alpha05" // Updated: May 8, 2019
    const val android_robolectric =             "4.3"           // Updated: May 30, 2019
    const val android_support =                 "1.1.0-alpha05" // Updated: May 08, 2019
    const val androidx_test =                   "1.2.0"         // Updated: May 31, 2019

    /* Publishing */
    const val publishing_bintray_plugin =       "1.8.4"         // Updated:
    const val publishing_dokka_plugin =         "0.9.18"        // Updated: Mar 19, 2019
    const val publishing_maven_plugin =         "2.1"           // Updated:
}

object Lib {

    /* Kotlin */
    const val kotlin =                          "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Version.kotlin}"
    const val coroutines_android =              "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines}"
    const val kotlin_reflect =                  "org.jetbrains.kotlin:kotlin-reflect:${Version.kotlin}"
    const val test =                            "org.jetbrains.kotlin:kotlin-test:${Version.kotlin}"
    const val test_junit =                      "org.jetbrains.kotlin:kotlin-test-junit:${Version.kotlin}"

    /* Other */
    const val ktor =                            "io.ktor:ktor-client-core:${Version.ktor}"
    const val ktor_apache =                     "io.ktor:ktor-client-apache:${Version.ktor}"
    const val ktor_android =                    "io.ktor:ktor-client-android:${Version.ktor}"
    const val mockk =                           "io.mockk:mockk:${Version.mockk}"
    const val mockk_android =                   "io.mockk:mockk-android:${Version.mockk}"

    /* Android */
    object Android {
        const val appcompat =                   "androidx.appcompat:appcompat:${Version.android_support}"
        const val constraintLayout =           "androidx.constraintlayout:constraintlayout:${Version.android_constraintLayout}"
        const val espresso =                    "androidx.test.espresso:espresso-core:${Version.android_espresso}"
        const val gradle_plugin =               "com.android.tools.build:gradle:${Version.android_gradle_plugin}"
        const val ktx =                         "androidx.core:core-ktx:${Version.android_ktx}"
        const val lifecycle =                   "androidx.lifecycle:lifecycle-runtime:${Version.android_lifecycle}"
        const val lifecycle_jdk8 =              "androidx.lifecycle:lifecycle-common-java8:${Version.android_lifecycle}"
        const val recyclerView =                "androidx.recyclerview:recyclerview:${Version.android_recyclerView}"
        const val robolectric =                 "org.robolectric:robolectric:${Version.android_robolectric}"
        const val support_annotations =         "com.android.support:support-annotations:28.0.0"
        const val test_runner =                 "com.android.support.test:runner:${Version.androidx_test}"
    }

    /* Publishing */
    object Publishing {
        const val bintray_plugin =                  "com.jfrog.bintray.gradle:gradle-bintray-plugin:${Version.publishing_bintray_plugin}"
        const val dokka_plugin =                    "org.jetbrains.dokka:dokka-android-gradle-plugin:${Version.publishing_dokka_plugin}"
        const val maven_plugin =                    "com.github.dcendents:android-maven-gradle-plugin:${Version.publishing_maven_plugin}"
    }
}
plugins {
    id("com.android.library" )
    id("kotlin-android" )
    id("kotlin-android-extensions" )
}

android { applyAndroidConfig() }

dependencies {
    applyAndroidTests()

    implementation( Libs.kotlin )
    implementation( Libs.coroutines_android )
    implementation( Libs.kotlin_reflect )

    implementation( Libs.ktor )
    implementation( Libs.ktor_apache )

    implementation( Libs.Android.appcompat )
    implementation( Libs.Android.ktx )
    implementation( Libs.Android.lifecycle )
    implementation( Libs.Android.lifecycle_jdk8 )
    implementation( Libs.Android.recycler_view )
    implementation( Libs.Android.support_annotations )
}

plugins {
    id("com.android.library" )
    id("kotlin-android" )
    id("kotlin-android-extensions" )
}

android { applyAndroidConfig() }

dependencies {
    applyAndroidTests()

    implementation( Lib.kotlin )
    implementation( Lib.coroutines_android )
    implementation( Lib.kotlin_reflect )

    implementation( Lib.ktor )
    implementation( Lib.ktor_android )

    implementation( Lib.Android.appcompat )
    implementation( Lib.Android.ktx )
    implementation( Lib.Android.lifecycle )
    implementation( Lib.Android.lifecycle_jdk8 )
    implementation( Lib.Android.recyclerView )
    implementation( Lib.Android.support_annotations )
}

publish("theia" )
applyDokka()
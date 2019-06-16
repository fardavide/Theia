plugins {
    id( "com.android.library" )
    id( "kotlin-android" )
    id( "kotlin-android-extensions" )
}

android { applyAndroidConfig() }

dependencies {
    api( Module.testAndroid )

    implementation( Lib.kotlin )
    implementation( Lib.coroutines )

    api( Lib.Android.espresso )
}

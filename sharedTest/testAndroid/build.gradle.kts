plugins {
    id( "com.android.library" )
    id( "kotlin-android" )
    id( "kotlin-android-extensions" )
}

android { applyAndroidConfig() }

dependencies {
    api( Module.testKotlin )

    implementation( Lib.kotlin )
    implementation( Lib.coroutines )
    implementation( Lib.Android.lifecycle )

    api( Lib.mockk_android )
    api( Lib.Android.test_runner )
}

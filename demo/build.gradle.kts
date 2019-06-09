plugins {
    id("com.android.application" )
    id("kotlin-android" )
    id("kotlin-android-extensions" )
}

android { applyAndroidConfig( "studio.forface.theia.demo", 18 ) }

dependencies {
    implementation( project(":theia" ) )
    applyAndroidTests()

    implementation( Lib.kotlin )
    implementation( Lib.coroutines_android )

    implementation( Lib.Android.appcompat )
    implementation( Lib.Android.constraintLayout )
    implementation( Lib.Android.ktx )
    implementation( Lib.Android.lifecycle )
    implementation( Lib.Android.recyclerView )
}

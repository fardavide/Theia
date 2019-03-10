plugins {
    id("com.android.application" )
    id("kotlin-android" )
    id("kotlin-android-extensions" )
}

android { applyAndroidConfig( "studio.forface.theia.demo", 18 ) }

dependencies {
    implementation( project(":theia" ) )
    applyAndroidTests()

    implementation( Libs.kotlin )

    implementation( Libs.Android.appcompat )
    implementation( Libs.Android.constraint_layout )
    implementation( Libs.Android.ktx )
    implementation( Libs.Android.lifecycle )
    implementation( Libs.Android.recycler_view )
}

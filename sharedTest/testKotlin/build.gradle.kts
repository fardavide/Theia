plugins {
    id( "java-library" )
    id( "kotlin" )
}

dependencies {
    implementation( Lib.kotlin )
    implementation( Lib.coroutines )

    api( Lib.test )
    api( Lib.test_junit )
    api( Lib.mockk )
    api( Lib.kotlintest_runner )
    api( Lib.coroutines_test )
}

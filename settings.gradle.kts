// Gradle
include( ":buildSrc" )

// Lib
include( ":theia" )

// Test
include( ":sharedTest:testKotlin" )
include( ":sharedTest:testAndroid" )
include( ":sharedTest:testAndroidInstrumented" )

// Demo
include( ":demo" )
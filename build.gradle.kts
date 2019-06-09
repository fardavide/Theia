buildscript {
    repositories( repos )
    dependencies( classpathDependencies )
}

allprojects {
    repositories( repos )
}

subprojects {
    tasks.withType( Javadoc::class ).all { enabled = false }
}

tasks.register("clean", Delete::class.java ) {
    delete( rootProject.buildDir )
}

@Suppress("UnstableApiUsage")
val test = tasks.getting( Test::class ) {
    useJUnitPlatform { }
}
import org.gradle.kotlin.dsl.KotlinBuildScript
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.gradle.DokkaAndroidTask
import java.io.File

/**
 * A script for apply Dokka Android Gradle plugin
 * @author Davide Farella
 */
@Suppress("unused")
fun KotlinBuildScript.applyDokka() {
    apply( plugin = "com.android.library" )
    apply( plugin = "kotlin-android" )
    apply( plugin = "org.jetbrains.dokka-android" )
    tasks.withType( DokkaAndroidTask::class ) {
        apiVersion = Project.targetSdk.toString()
        jdkVersion = Project.jdkVersion.ordinal
        sourceDirs = listOf( File( "src/main/kotlin" ) )
        outputFormat = "html"
        outputDirectory = "docs"
    }
}

package studio.forface.theia

import androidx.appcompat.app.AppCompatActivity
import io.mockk.every
import io.mockk.mockk
import studio.forface.theia.dsl.imageUrl
import studio.forface.theia.dsl.invoke
import studio.forface.theia.dsl.newTheiaInstance
import studio.forface.theia.testAndroid.TestLifecycle
import kotlin.test.Test

/**
 * Test class for Theia
 * @author Davide Giuseppe Farella
 */
class TheiaTest {

    @Test
    fun `Requests are cleaned when the component is destroyed`() {
        val lifecycle = TestLifecycle().apply { start() }
        val activity = mockk<AppCompatActivity>( relaxed = true ) {
            every { this@mockk.lifecycle } returns lifecycle.registry
        }

        val theiaInstance = activity.newTheiaInstance
        val params = theiaParams {
            imageUrl = "https://googlechrome.github.io/samples/picture-element/images/butterfly.jpg"
            dimensions = 10 to 10
            onCompletion { /* noop */ }
        }

        // Run the call
        theiaInstance( params ) {  }

        lifecycle.destroy()

        // Run the call again
        theiaInstance( params ) {  }
    }
}
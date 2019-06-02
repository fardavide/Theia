package studio.forface.theia.imageManipulation

import android.graphics.Bitmap
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import studio.forface.theia.BitmapResponse
import studio.forface.theia.TheiaResponse
import kotlin.test.Test

/**
 * Test class for [ImageTransformer]
 * @author Davide Giuseppe Farella
 */
class ImageTransformerTest {

    private fun theiaTransformer( image: TheiaResponse ) = spyk( TheiaTransformer( image ) ) {
        every { crop( any() ) }
    }

    class TestTransformer( image: TheiaResponse ) : TheiaTransformer( image )

    @Test
    fun test() {
        val image = BitmapResponse( mockk() )
        val result = TheiaTransformer( image ) {
            crop( 1 to 2 )
            crop( 1 to 2 )
        }
        assert( result.image is Bitmap )
    }
}
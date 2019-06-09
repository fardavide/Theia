package studio.forface.theia.imageManipulation

import android.graphics.Bitmap
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import studio.forface.theia.BitmapResponse

/**
 * Test class for [ImageTransformer]
 * @author Davide Giuseppe Farella
 */
internal class ImageTransformerTest : StringSpec( {

    "BaseTheiaTransformer works correctly" {
        val transformer = object : BaseTheiaTransformer(
            bitmapTransformer = mockk {
                every { any<Bitmap>().center(any()) } answers { firstArg() }
            }
        ) {}

        val image = BitmapResponse( mockk() )
        val result = transformer( image ) {
            it.center( 1 to 2 )
        }
        assert( result.image is Bitmap )
    }
} )
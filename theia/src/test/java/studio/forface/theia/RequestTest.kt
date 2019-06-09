package studio.forface.theia

import io.kotlintest.matchers.string.contain
import io.kotlintest.should
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import studio.forface.theia.AbsSyncImageSource.DrawableImageSource

/**
 * Test class for [TheiaRequest]
 * @author Davide Giuseppe Farella
 */
class RequestTest : StringSpec( {

    "forceBitmap converts the Drawable into Bitmap" {
        val params = mockk<RequestParams>( relaxed = true ) { every { forceBitmap } returns true }
        val source = mockk<DrawableImageSource> { every { source } returns mockk( relaxed = true ) }

        // Assert that request is trying to convert into Bitmap
        val exception = shouldThrow<TheiaException> { SyncRequest( params )( source ) }
        exception.message should contain( "Method createBitmap in android.graphics.Bitmap not mocked." )
    }
} )
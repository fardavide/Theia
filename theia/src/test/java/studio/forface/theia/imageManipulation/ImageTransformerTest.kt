package studio.forface.theia.imageManipulation

import studio.forface.theia.Dimensions
import studio.forface.theia.height
import studio.forface.theia.width
import kotlin.test.Test

/**
 * Test class for [ImageTransformer]
 * @author Davide Giuseppe Farella
 */
class ImageTransformerTest {

    object TestTransformer : ImageTransformer<Int> {
        override fun Int.softCrop( dimensions: Dimensions ): Int {
            return this + dimensions.width + dimensions.height
        }
    }

    @Test
    fun test() {
        val result = TestTransformer( 4 ) {
            softCrop( 1 to 2 )
            softCrop( 3 to 4 )
            softCrop( 5 to 6 )
        }

        println( result ) // Should be 25
        // 4 + 1 + 2 = 7
        // 7 + 3 + 4 = 14
        // 14 + 5 + 6 = 25
    }
}
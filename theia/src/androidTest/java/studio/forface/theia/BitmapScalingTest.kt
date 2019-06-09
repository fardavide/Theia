package studio.forface.theia

import android.graphics.Bitmap
import androidx.core.graphics.createBitmap
import studio.forface.theia.utils.centerTo
import studio.forface.theia.utils.cropTo
import studio.forface.theia.utils.fitTo
import studio.forface.theia.utils.stretchTo
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Testing the scaling of [Bitmap]
 *
 * @author Davide Giuseppe Farella
 */
class BitmapScalingTest {

    @Test
    fun center_wide_to_square() {
        val bitmap = createBitmap(20, 10 )
        val dimensions = 50 to 50

        val new = bitmap.centerTo( dimensions )

        assertEquals(50, new.width )
        assertEquals(25, new.height )
    }

    @Test
    fun crop_to_smalled_square() {
        val bitmap = createBitmap(200, 100 )
        val dimensions = 50 to 50

        val new = bitmap.cropTo( dimensions )

        assertEquals(50, new.width )
        assertEquals(50, new.height )
    }

    @Test
    fun crop_to_larger_square() {
        val bitmap = createBitmap(20, 10 )
        val dimensions = 50 to 50

        val new = bitmap.cropTo( dimensions )

        assertEquals(50, new.width )
        assertEquals(50, new.height )
    }

    @Test
    fun fit_square_to_smaller_square() {
        val bitmap = createBitmap(100,100 )
        val dimensions = 50 to 50

        val new = bitmap.fitTo( dimensions )

        assertEquals(50, new.width )
        assertEquals(50, new.height )
    }

    @Test
    fun fit_square_to_wide() {
        val bitmap = createBitmap(10,10 )
        val dimensions = 50 to 20

        val new = bitmap.fitTo( dimensions )

        assertEquals(50, new.width )
        assertEquals(20, new.height )
    }

    @Test
    fun stretch_to_wide() {
        val bitmap = createBitmap(10,10 )
        val dimensions = 50 to 20

        val new = bitmap.stretchTo( dimensions )

        assertEquals(50, new.width )
        assertEquals(20, new.height )
    }
}
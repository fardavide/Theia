package studio.forface.theia.imageManipulation

import android.graphics.*
import studio.forface.theia.Dimensions
import studio.forface.theia.utils.centerTo
import studio.forface.theia.utils.cropTo
import studio.forface.theia.utils.fitTo
import studio.forface.theia.utils.stretchTo

/**
 * A base [ImageTransformer] for [Bitmap]
 *
 * @author Davide Giuseppe Farella
 */
open class TheiaBitmapTransformer : ImageTransformer<Bitmap> {

    // region Size transformations
    /**
     * Scale the Image to fit the given [Dimensions]. The whole image will be visible and a transparent background
     * will be applied to fill
     *
     * Original dimensions will change
     * Original proportions will be maintained
     *
     * @return [Bitmap]
     */
    override fun Bitmap.center( dimensions: Dimensions ) = centerTo( dimensions )

    /**
     * Crop the Image if exceed the given [Dimensions] else a transparent background will be applied to fill
     *
     * Original dimensions will be maintained
     * Original proportions will be maintained
     *
     * @return [Bitmap]
     */
    override fun Bitmap.crop( dimensions: Dimensions ) = cropTo( dimensions )

    /**
     * Scale the Image to fill the given [Dimensions]. The image will be cropped to fit the dimensions
     *
     * Original dimensions will change
     * Original proportions will be maintained
     *
     * @return [Bitmap]
     */
    override fun Bitmap.fit( dimensions: Dimensions ) = fitTo( dimensions )

    /**
     * Stretch the Image to the given [Dimensions]
     *
     * Original dimensions will change
     * Original proportions will be ignored
     *
     * @return [Bitmap]
     */
    override fun Bitmap.stretch( dimensions: Dimensions ) = stretchTo( dimensions )
    // endregion

    /**
     * Apply a Round shape to the Image
     *
     * @return [Bitmap]
     */
    override fun Bitmap.round() : Bitmap {
        val size = Math.min( width, height )

        val x = ( width - size ) / 2
        val y = ( height - size ) / 2

        val squaredBitmap = Bitmap.createBitmap( this, x, y, size, size )
        if ( squaredBitmap != this ) {
            recycle()
        }

        val bitmap = Bitmap.createBitmap( size, size, config )

        val canvas = Canvas( bitmap )
        val paint = Paint()
        val shader = BitmapShader( squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP )
        paint.shader = shader
        paint.isAntiAlias = true

        val r = size / 2f
        canvas.drawCircle( r, r, r, paint )

        squaredBitmap.recycle()
        return bitmap
    }
}
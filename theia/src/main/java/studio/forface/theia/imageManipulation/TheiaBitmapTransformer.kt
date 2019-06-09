package studio.forface.theia.imageManipulation

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import studio.forface.theia.Corners
import studio.forface.theia.Dimensions
import studio.forface.theia.utils.*

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
     * Apply a Circle shape to the Image
     *
     * @return [Bitmap]
     */
    override fun Bitmap.circle() = toCircle()

    /**
     * Apply a Rounded shape to the Image
     * @return [Drawable]
     */
    override fun Bitmap.round( corners: Corners ) = roundTo( corners )
}
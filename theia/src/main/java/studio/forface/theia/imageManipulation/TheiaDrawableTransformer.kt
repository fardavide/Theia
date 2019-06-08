package studio.forface.theia.imageManipulation

import android.graphics.drawable.Drawable
import studio.forface.theia.Dimensions
import studio.forface.theia.utils.*

/**
 * A base [ImageTransformer] for [Drawable]
 *
 * @author Davide Giuseppe Farella
 */
open class TheiaDrawableTransformer : ImageTransformer<Drawable> {

    /**
     * Scale the Image to fit the given [Dimensions]. The whole image will be visible and a transparent background
     * will be applied to fill
     *
     * Original dimensions will change
     * Original proportions will be maintained
     *
     * @return [Drawable]
     */
    override fun Drawable.center( dimensions: Dimensions ) = centerTo( dimensions )

    /**
     * Crop the Image if exceed the given [Dimensions] else a transparent background will be applied to fill
     *
     * Original dimensions will be maintained
     * Original proportions will be maintained
     *
     * @return [Drawable]
     */
    override fun Drawable.crop( dimensions: Dimensions ) = cropTo( dimensions )

    /**
     * Scale the Image to fill the given [Dimensions]. The image will be cropped to fit the dimensions
     *
     * Original dimensions will change
     * Original proportions will be maintained
     *
     * @return [Drawable]
     */
    override fun Drawable.fit( dimensions: Dimensions ) = fitTo( dimensions )

    /**
     * Stretch the Image to the given [Dimensions]
     *
     * Original dimensions will change
     * Original proportions will be ignored
     *
     * @return [Drawable]
     */
    override fun Drawable.stretch( dimensions: Dimensions ) = stretchTo( dimensions )
    // endregion

    /**
     * Apply a Round shape to the Image
     * @return [Drawable]
     */
    override fun Drawable.round() = this.andLogNotImplemented()
}
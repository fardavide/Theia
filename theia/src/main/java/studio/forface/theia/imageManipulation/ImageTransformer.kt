package studio.forface.theia.imageManipulation

import studio.forface.theia.Dimensions
import studio.forface.theia.TheiaParams.Shape

/**
 * Interface containing available transformations for [Image]s
 * @author Davide Giuseppe Farella
 */
interface ImageTransformer<Image> {

    // region Size transformations
    /**
     * Scale the [Image] to fit the given [Dimensions]. The whole image will be visible and a transparent background
     * will be applied to fill
     *
     * Original dimensions will change
     * Original proportions will be maintained
     *
     * @returnImage
     */
    fun Image.center( dimensions: Dimensions ) : Image

    /**
     * Crop the [Image] if exceed the given [Dimensions] else a transparent background will be applied to fill
     *
     * Original dimensions will be maintained
     * Original proportions will be maintained
     *
     * @returnImage
     */
    fun Image.crop( dimensions: Dimensions ) : Image

    /**
     * Scale the [Image] to fill the given [Dimensions]. The image will be cropped to fit the dimensions
     *
     * Original dimensions will change
     * Original proportions will be maintained
     *
     * @returnImage
     */
    fun Image.fit( dimensions: Dimensions ) : Image

    /**
     * Stretch the [Image] to the given [Dimensions]
     *
     * Original dimensions will change
     * Original proportions will be ignored
     *
     * @returnImage
     */
    fun Image.stretch( dimensions: Dimensions ) : Image
    // endregion

    /**
     * Apply a [Shape.Round] to the [Image]
     * @returnImage
     */
    fun Image.round() : Image
}

/**
 * [invoke] operator for use the receiver [T] [ImageTransformer] of the given lambda [block]
 *
 * @param image [I] to transform
 * @param block [TransformationPipeline] that will transform the Image
 *
 * @return image [I]
 */
inline operator fun <T : ImageTransformer<I>, I : Any> T.invoke(
    image: I,
    block: TransformationPipeline<I>
) = block( image )

/** Typealias of a lambda that takes [ImageTransformer] as receiver and Image as parameter and return an Image */
typealias TransformationPipeline<Image> = ImageTransformer<Image>.(Image) -> Image

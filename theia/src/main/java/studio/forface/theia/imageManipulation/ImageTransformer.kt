package studio.forface.theia.imageManipulation

import studio.forface.theia.Dimensions

/**
 * Interface containing available transformations for [Image]s
 * @author Davide Giuseppe Farella
 */
interface ImageTransformer<Image> {

    /** @return transformed [Image] */
    val image: Image

    /**
     * Scale the [Image] to fit the given [Dimensions]. The whole image will be visible and a transparent background
     * will be applied to fill
     *
     * Original dimensions will change
     * Original proportions will be maintained
     */
    fun center( dimensions: Dimensions )

    /**
     * Crop the [Image] if exceed the given [Dimensions] else a transparent background will be applied to fill
     *
     * Original dimensions will be maintained
     * Original proportions will be maintained
     */
    fun crop( dimensions: Dimensions )

    /**
     * Scale the [Image] to fill the given [Dimensions]. The image will be cropped to fit the dimensions
     *
     * Original dimensions will change
     * Original proportions will be maintained
     */
    fun fit( dimensions: Dimensions )

    /**
     * Stretch the [Image] to the given [Dimensions]
     *
     * Original dimensions will change
     * Original proportions will be ignored
     */
    fun stretch( dimensions: Dimensions )
}

/**
 * [invoke] operator for use the receiver [T] [ImageTransformer] of the given lambda [block]
 *
 * @param block [TransformationPipeline] that will transform the Image
 */
inline operator fun <reified T : ImageTransformer<I>, I : Any> T.invoke(
    block: TransformationPipeline<I>
) : I{
    block()
    return image
}

/** Typealias of a lambda that takes [ImageTransformer] and return an Image */
typealias TransformationPipeline<Image> = ImageTransformer<Image>.() -> Unit

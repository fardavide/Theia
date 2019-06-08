@file:Suppress("unused") // API methods

package studio.forface.theia.imageManipulation

import studio.forface.theia.*
import studio.forface.theia.log.TheiaLogger

/**
 * Default [ImageTransformer] for [TheiaResponse]
 * Inherit from [BaseTheiaTransformer]
 *
 * @author Davide Giuseppe Farella
 */
object TheiaTransformer : BaseTheiaTransformer()

/**
 * A base [ImageTransformer] for [TheiaResponse]
 *
 * Implements [TheiaLogger]
 *
 *
 * @param bitmapTransformer an [ImageTransformer] of `Bitmap` for [BitmapResponse]
 * Default is [TheiaBitmapTransformer]
 *
 * @param drawableTransformer an [ImageTransformer] of `Drawable` for [DrawableResponse]
 * Default is [TheiaDrawableTransformer]
 *
 * @param logger [TheiaLogger]
 * Default is [TheiaConfig.logger]
 */
abstract class BaseTheiaTransformer (
    private val bitmapTransformer: TheiaBitmapTransformer = TheiaBitmapTransformer(),
    private val drawableTransformer: TheiaDrawableTransformer = TheiaDrawableTransformer(),
    logger: TheiaLogger = TheiaConfig.logger
) : ImageTransformer<TheiaResponse>, TheiaLogger by logger {

    /**
     * Transform [ResponseWrapper.image] of the receiver [TheiaResponse] - using [bitmapTransformer] of
     * [drawableTransformer] - with the given [pipeline]
     *
     * @throws IllegalPipelineReceiverException if the [pipeline] has been called on an invalid receiver
     *
     * @return [TheiaResponse]
     */
    protected inline fun TheiaResponse.transformImage( pipeline: TransformationPipeline<Any> ) : TheiaResponse {
        return with( transformer ) {
            try {
                pipeline( this@transformImage.image )
            } catch ( e: ClassCastException ) {
                throw IllegalPipelineReceiverException( e )
            }.toTheiaResponse()
        }
    }

    /**
     * @return [ImageTransformer] or [Any], which is [bitmapTransformer] or [drawableTransformer] for the receiver
     * [TheiaResponse]
     */
    @PublishedApi // Needed for inline
    internal val TheiaResponse.transformer : ImageTransformer<Any> get() {
        @Suppress("UNCHECKED_CAST") // No check needed for cast to Any
        return when ( this ) {
            is BitmapResponse -> bitmapTransformer
            is DrawableResponse -> drawableTransformer
        } as ImageTransformer<Any>
    }

    // region Size transformations
    /**
     * Scale the Image to fit the given [Dimensions]. The whole image will be visible and a transparent background
     * will be applied to fill
     *
     * Original dimensions will change
     * Original proportions will be maintained
     *
     * @return [TheiaResponse]
     */
    override fun TheiaResponse.center( dimensions: Dimensions ) = transformImage { it.center( dimensions ) }

    /**
     * Crop the Image if exceed the given [Dimensions] else a transparent background will be applied to fill
     *
     * Original dimensions will be maintained
     * Original proportions will be maintained
     *
     * @return [TheiaResponse]
     */
    override fun TheiaResponse.crop( dimensions: Dimensions ) = transformImage { it.crop( dimensions ) }

    /**
     * Scale the Image to fill the given [Dimensions]. The image will be cropped to fit the dimensions
     *
     * Original dimensions will change
     * Original proportions will be maintained
     *
     * @return [TheiaResponse]
     */
    override fun TheiaResponse.fit( dimensions: Dimensions ) = transformImage { it.fit( dimensions ) }

    /**
     * Stretch the Image to the given [Dimensions]
     *
     * Original dimensions will change
     * Original proportions will be ignored
     *
     * @return [TheiaResponse]
     */
    override fun TheiaResponse.stretch( dimensions: Dimensions ) = transformImage { it.stretch( dimensions ) }
    // endregion

    /**
     * Apply a Round shape to the Image
     * @return [TheiaResponse]
     */
    override fun TheiaResponse.round() = transformImage { it.round() }
}


/**
 * Transform the receiver [TheiaResponse] with the given [transformer]
 *
 * @param [T] subtype of [BaseTheiaTransformer], type of [transformer]
 * @param [transformer] [T] that will be used for run the [pipeline]
 *
 * @return [TheiaResponse]
 */
inline fun <T : BaseTheiaTransformer> TheiaResponse.transform(
    transformer: T,
    pipeline: TransformationPipeline<TheiaResponse>
): TheiaResponse {
    return with( transformer ) {
        pipeline( this@transform )
    }
}

/**
 * Transform the receiver [TheiaResponse] with [TheiaTransformer]
 * @return [TheiaResponse]
 */
inline fun TheiaResponse.transform(
    pipeline: TransformationPipeline<TheiaResponse>
): TheiaResponse {
    return with( TheiaTransformer ) {
        pipeline( this@transform )
    }
}
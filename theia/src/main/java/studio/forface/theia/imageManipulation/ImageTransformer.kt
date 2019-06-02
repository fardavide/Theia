package studio.forface.theia.imageManipulation

import studio.forface.theia.Dimensions
import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * Interface containing available transformations for [Image]s
 * @author Davide Giuseppe Farella
 */
interface ImageTransformer<Image> {

    /**
     * Scale the [Image] to fit the given [Dimensions]. The whole image will be visible and a transparent background
     * will be applied to fill
     *
     * Original dimensions will change
     * Original proportions will be maintained
     */
    fun Image.center( dimensions: Dimensions ) : Image

    /**
     * Crop the [Image] if exceed the given [Dimensions] else a transparent background will be applied to fill
     *
     * Original dimensions will be maintained
     * Original proportions will be maintained
     */
    fun Image.crop( dimensions: Dimensions ) : Image

    /**
     * Scale the [Image] to fill the given [Dimensions]. The image will be cropped to fit the dimensions
     *
     * Original dimensions will change
     * Original proportions will be maintained
     */
    fun Image.fit( dimensions: Dimensions ) : Image

    /**
     * Stretch the [Image] to the given [Dimensions]
     *
     * Original dimensions will change
     * Original proportions will be ignored
     */
    fun Image.strech( dimensions: Dimensions ) : Image

}

/**
 * [invoke] operator for use the receiver [T] and given [image] as [TransformerDualReceiver] of the given lambda [block]
 *
 * @param image [I] image to transform with the pipeline
 * @param block [TransformerPipeline] that will transform the given [image]
 */
inline operator fun <reified T : ImageTransformer<I>, I : Any> T.invoke(
    image: I,
    block: TransformerPipeline<I>
) : I {
    val context = TransformerContext( this.wrap(), image )
    return context.block( image ).unwrap()
}

/** Typealias for a lambda that takes [TransformerDualReceiver] and returns an [ImageWrapper] */
typealias TransformerPipeline<Image> = TransformerDualReceiver<Image>.(Image) -> ImageWrapper<Image>

/** Typealias for a [TransformerContext] with [WrappedTransformer] and [ImageWrapper] */
typealias TransformerDualReceiver<Image> = TransformerContext<WrappedTransformer<Image>, ImageWrapper<Image>, Image>

/** Typealias for an [ImageTransformer] of [ImageWrapper] */
internal typealias WrappedTransformer<Image> = ImageTransformer<ImageWrapper<Image>>


/**
 * Class that wraps the [Image] to transform.
 * We use it so [TransformerContext] can inherit from the [Image] ( by this open wrapper )
 */
open class ImageWrapper<Image>( private var image: Image ) {

    /** @return [Image] wrapped [image] */
    fun unwrap() = image

    /** Update the wrapper [image] after the transformation */
    fun update( new: Image ) {
        image = new
    }
}

/**
 * Class that extends [ImageWrapper] and implements [ImageTransformer] for simulate a dual receiver
 *
 * @param T [ImageTransformer] of [I]
 * @param W [ImageWrapper] of [I]
 * @param I type of the wrapped Image
 */
class TransformerContext<
    T : ImageTransformer<W>,
    W: ImageWrapper<I>,
    I : Any
> @PublishedApi internal constructor(
    transformer : T,
    image: I
) : ImageWrapper<I>( image ), ImageTransformer<W> by transformer

/**
 * Create a new instance of [WrappedTransformer] of [I] from the receiver [ImageTransformer] of [I]
 * @return [WrappedTransformer] of [I]
 *
 * @param T receiver [ImageTransformer] of [I]
 * @param W [ImageWrapper] of [I]
 * @param I type of the wrapped Image
 */
@PublishedApi
@Suppress("UNCHECKED_CAST") // We can't avoid unchecked cast with Proxy
internal inline fun <
    reified T : ImageTransformer<I>,
    W: ImageWrapper<I>,
    I : Any
> T.wrap(): WrappedTransformer<I> {

    return Proxy.newProxyInstance(
        T::class.java.classLoader,
        arrayOf( ImageTransformer::class.java )
    ) { _: Any, method: Method, args: Array<out Any> ->

        // Get the ImageWrapper from first argument
        val wrapper = args[0] as W
        // transform the image wrapped in wrapper
        val transformed = method.invoke( this, wrapper.unwrap(), args[1] ) as I
        // Update the ImageWrapper with the transformed image
        wrapper.update( transformed )
        // return the ImageWrapper
        wrapper

    } as WrappedTransformer<I>
}
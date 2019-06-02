package studio.forface.theia.imageManipulation

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import studio.forface.theia.BitmapResponse
import studio.forface.theia.DrawableResponse
import studio.forface.theia.TheiaResponse

/**
 * A base [ImageTransformer] for [TheiaResponse]
 *
 * @param baseImage the Image to transform
 *
 *
 * @author Davide Giuseppe Farella
 */
@Suppress("UNCHECKED_CAST") // subTransformer as ImageTransformer<TheiaResponse>
open class TheiaTransformer (
    baseImage: TheiaResponse,
    private val subTransformer : ImageTransformer<*> = when( baseImage ) {
        is BitmapResponse -> TheiaBitmapTransformer( baseImage.image )
        is DrawableResponse -> TheiaDrawableTransformer( baseImage.image )
    }
) : ImageTransformer<TheiaResponse> by subTransformer as ImageTransformer<TheiaResponse> {

    companion object {

        /**
         * [invoke] operator if companion object for make the call in a more fluent way
         * i.e. `TheiaTransformer( myResponse ) { ... }`
         */
        inline operator fun invoke(
            image: TheiaResponse,
            block: TransformationPipeline<TheiaResponse>
        ) = TheiaTransformer( image ).invoke( block )
    }

    /** @return transformed [TheiaResponse] */
    override val image: TheiaResponse get() {
            return when( val subImage = subTransformer.image ) {
                is Bitmap -> BitmapResponse( subImage )
                is Drawable -> DrawableResponse( subImage )
                else -> throw AssertionError()
            }
        }
}
package studio.forface.theia

import android.widget.ImageView
import studio.forface.theia.dsl.AbsTheiaBuilder
import studio.forface.theia.dsl.CompletionCallback
import studio.forface.theia.dsl.ErrorCallback
import studio.forface.theia.transformation.CircleTransformation
import studio.forface.theia.transformation.NoTransformation
import studio.forface.theia.transformation.TheiaTransformation

/**
 * Required params for apply the image into [ImageView]
 *
 * @author Davide Giuseppe Farella
 */
data class TheiaParams(
    internal val image: ImageSource,
    internal val target: ImageView?,
    internal val placeholder: SyncImageSource?,
    internal val error: ImageSource?,
    internal val scaleError: Boolean,
    internal val scalePlaceholder: Boolean,
    internal val scaleType: ScaleType,
    internal val shape: Shape,
    internal val extraTransformations: List<TheiaTransformation>,
    internal val animationLoop: AnimationLoop,
    internal val placeholderAnimationLoop: AnimationLoop,
    internal val errorAnimationLoop: AnimationLoop,
    internal val useCache: Boolean,
    internal val forceBitmap: Boolean,
    internal val dimensions: Dimensions?,
    internal val completionCallback: CompletionCallback = {},
    internal val errorCallback: ErrorCallback = {}
) {

    /** An enum representing how to scale the image */
    enum class ScaleType {

        /**
         * The image will be resized to be centered in the `ImageView`
         * Proportions will be preserved, the image will be fully visible and so one dimension will be smaller than
         * the `ImageView`
         */
        Center,

        /**
         * The image will cropped to the size of the `ImageView`
         * No resizing will be applied
         */
        Crop,

        /**
         * The image will be resized to fit the `ImageView`
         * Proportions will be preserved, the image will be partially visible on its width or height as long as the
         * `ImageView` has not the same proportions as the image.
         */
        Fit,

        /**
         * The image will be stretched to fill the `ImageView`
         * Proportions won't be preserved and the image will be distorted
         */
        Stretch
    }

    /** A custom shape for the image to load */
    sealed class Shape( val transformation: TheiaTransformation ) {
        object Round : Shape( CircleTransformation )
        object Square : Shape( NoTransformation )
    }
}

/**
 * A function that takes an [ExtraParams] block and return the same block.
 * It's purpose is to create a block that will create [TheiaParams] that can be applied on another instance of
 * [AbsTheiaBuilder].
 *
 * Example:
> val extraParams = theiaParams {
    imageUrl = someImageUrl
    shape = Round
 }
 someImageView.theia( extraParams ) {
    scaleType = Center
 }
 */
fun theiaParams( block: ExtraParams ) = block

/**
 * A typealias for a lambda that takes [AbsTheiaBuilder] as receiver and return [Unit]
 * This represents an block that will create optional [TheiaParams] for the request.
 *
 * @see theiaParams
 */
typealias ExtraParams = AbsTheiaBuilder.() -> Unit
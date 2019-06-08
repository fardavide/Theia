@file:Suppress("MemberVisibilityCanBePrivate")

package studio.forface.theia.dsl

import android.content.res.Resources
import android.widget.ImageView
import studio.forface.theia.*
import studio.forface.theia.TheiaConfig.defaultError
import studio.forface.theia.TheiaConfig.defaultForceBitmap
import studio.forface.theia.TheiaConfig.defaultPlaceholder
import studio.forface.theia.TheiaConfig.defaultScaleError
import studio.forface.theia.TheiaConfig.defaultScalePlaceholder
import studio.forface.theia.TheiaConfig.defaultScaleType
import studio.forface.theia.TheiaConfig.defaultShape
import studio.forface.theia.TheiaConfig.defaultUseCache
import studio.forface.theia.transformation.TheiaTransformation

/**
 * A builder for [ITheia]
 *
 * @author Davide Giuseppe Farella
 */
@TheiaDsl
abstract class AbsTheiaBuilder internal constructor ( internal val resources: Resources ) {

    /** The [AsyncImageSource] for the image to load into [target] */
    var image: ImageSource? = null

    /** The [ImageView] where to load the [image]. Default is `null` */
    protected open var target: ImageView? = null

    /**
     * The image to load as placeholder for async requests.
     * It will be ignored for *successful* [Sync] requests, but it will be used anyway if:
     * * there is an error loading [image] and no [error] is supplied
     * * [error] is [Async]
     *
     * Default is [TheiaConfig.defaultPlaceholder]
     */
    var placeholder: SyncImageSource? = defaultPlaceholder

    /**
     * The image to load if some error occurred while loading [image]
     * Default is [TheiaConfig.defaultError]
     */
    var error: ImageSource? = defaultError

    /**
     * Override dimensions of images to load, this param is required if [target] is not set
     * Default is `null`
     */
    var dimensions: Dimensions? = null

    /**
     * If `true` [error] will respect [scaleType], else use [TheiaParams.ScaleType.Center]
     * Default is [TheiaConfig.defaultScaleError]
     */
    var scaleError = defaultScaleError

    /**
     * If `true` [placeholder] will respect [scaleType], else use [TheiaParams.ScaleType.Center]
     * Default is [TheiaConfig.defaultScalePlaceholder]
     */
    var scalePlaceholder = defaultScalePlaceholder

    /**
     * The [TheiaParams.ScaleType] to apply to [image]
     * Default is [TheiaConfig.defaultScaleType]
     */
    var scaleType = defaultScaleType

    /**
     * The [TheiaParams.Shape] to apply to [image]
     * Default is [TheiaConfig.defaultShape]
     */
    var shape: TheiaParams.Shape = defaultShape

    /** If `true` cache will be used for this request. Default is [TheiaConfig.defaultUseCache] */
    var useCache = defaultUseCache

    /**
     * If `true` the Images will be transformed as `Bitmap` even if they're `Drawable`.
     * Default is [TheiaConfig.defaultForceBitmap]
     */
    var forceBitmap = defaultForceBitmap

    /** Set a [CompletionCallback] that will be called when [image] is ready */
    fun onCompletion( callback: CompletionCallback ) {
        this.completionCallback = callback
    }

    /** Set an [ErrorCallback] that will be called when something went wrong loading [image] */
    fun onError( callback: ErrorCallback ) {
        this.errorCallback = callback
    }

    /**
     * Add a new [TheiaTransformation] to [extraTransformations] within plus operator.
     * E.g. >
    theia {
        ...
        + SomeCustomTransformation
        + AnotherTransformation
    }
     */
    operator fun plus( transformation: TheiaTransformation ) {
        extraTransformations += transformation
    }

    /**
     * @return [image] or [placeholder]
     * @throws ImageSourceNotSetException if both of them are null
     */
    private val actualImage get() = image ?: placeholder ?: throw ImageSourceNotSetException()

    /**
     * A [CompletionCallback] that will be called when [image] is ready
     * Default is `null`
     * Set it via [onCompletion] function
     */
    private var completionCallback: CompletionCallback? = null

    /**
     * An [ErrorCallback] that will be called when something went wrong loading [image]
     * Default is empty lambda
     * Set it via [onError] function
     */
    private var errorCallback: ErrorCallback = {}

    /** A [Set] of [TheiaTransformation] to apply to the images ( [image], [placeholder], [error] ) */
    private val extraTransformations = mutableListOf<TheiaTransformation>()

    /**
     * @return [TheiaParams]
     *
     * @throws PointlessRequestException if both [target] and [completionCallback] are null
     * @throws UndefinedDimensionsException if both [target] and [dimensions] are null
     */
    fun build(): TheiaParams {

        if ( target == null ) {
            if ( completionCallback == null ) throw PointlessRequestException()
            if ( dimensions == null ) throw UndefinedDimensionsException()
        }

        return TheiaParams(
            image =                 actualImage,
            target =                target,
            placeholder =           placeholder,
            error =                 error ?: placeholder,
            scaleError =            scaleError,
            scalePlaceholder =      scalePlaceholder,
            scaleType =             scaleType,
            shape =                 shape,
            extraTransformations =  extraTransformations,
            useCache =              useCache,
            forceBitmap =           forceBitmap,
            dimensions =            dimensions,
            completionCallback =    completionCallback ?: {},
            errorCallback =         errorCallback
        )
    }
}

/** Implementation of [AbsTheiaBuilder] that receives [AbsTheiaBuilder.target] as constructor params */
@TheiaDsl
class PreTargetedTheiaBuilder( resources: Resources, override var target: ImageView? ): AbsTheiaBuilder( resources )

/** Default implementation of [AbsTheiaBuilder] that exposes [AbsTheiaBuilder.target] */
@TheiaDsl
class TheiaBuilder( resources: Resources ): AbsTheiaBuilder( resources ) {
    public override var target: ImageView? = null
}

/** A typealias for a lambda that receives a [TheiaResponse] */
typealias CompletionCallback = suspend (TheiaResponse) -> Unit

/** A typealias for a lambda that receives a [TheiaException] */
typealias ErrorCallback = suspend (TheiaException) -> Unit
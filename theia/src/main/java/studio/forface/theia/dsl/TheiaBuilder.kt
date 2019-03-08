@file:Suppress("MemberVisibilityCanBePrivate")

package studio.forface.theia.dsl

import android.content.res.Resources
import android.widget.ImageView
import studio.forface.theia.*
import studio.forface.theia.TheiaConfig.defaultScaleError
import studio.forface.theia.TheiaConfig.defaultScalePlaceholder
import studio.forface.theia.TheiaConfig.defaultScaleType
import studio.forface.theia.TheiaConfig.defaultShape
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

    /** The [ImageView] where to load the [image] */
    protected open lateinit var target: ImageView

    /**
     * The image to load as placeholder for async requests.
     * It will be ignored for *successful* [Sync] requests, but it will be used anyway if:
     * * there is an error loading [image] and no [error] is supplied
     * * [error] is [Async]
     */
    var placeholder: SyncImageSource? = null

    /** The image to load if some error occurred while loading [image] */
    var error: ImageSource? = null

    /** If `true` [error] will respect [scaleType], else use [TheiaParams.ScaleType.Center] */
    var scaleError = defaultScaleError

    /** If `true` [placeholder] will respect [scaleType], else use [TheiaParams.ScaleType.Center] */
    var scalePlaceholder = defaultScalePlaceholder

    /** The [TheiaParams.ScaleType] to apply to [image] */
    var scaleType = defaultScaleType

    /** The [TheiaParams.Shape] to apply to [image] */
    var shape: TheiaParams.Shape = defaultShape

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

    /** A [Set] of [TheiaTransformation] to apply to the images ( [image], [placeholder], [error] ) */
    private val extraTransformations = mutableListOf<TheiaTransformation>()

    /**
     * @return [TheiaParams]
     *
     * @throws TargetNotSetException if [target] is not initialized
     */
    fun build(): TheiaParams {
        if ( ! ::target.isInitialized ) throw TargetNotSetException()

        return TheiaParams(
            image =                 actualImage,
            target =                target,
            placeholder =           placeholder,
            error =                 error ?: placeholder,
            scaleError =            scaleError,
            scalePlaceholder =      scalePlaceholder,
            scaleType =             scaleType,
            shape =                 shape,
            extraTransformations =  extraTransformations
        )
    }
}

/** Implementation of [AbsTheiaBuilder] that receives [AbsTheiaBuilder.target] as constructor params */
@TheiaDsl
class PreTargetedTheiaBuilder( resources: Resources, override var target: ImageView ): AbsTheiaBuilder( resources )

/** Default implementation of [AbsTheiaBuilder] that exposes [AbsTheiaBuilder.target] */
@TheiaDsl
class TheiaBuilder( resources: Resources ): AbsTheiaBuilder( resources ) {
    public override lateinit var target: ImageView
}
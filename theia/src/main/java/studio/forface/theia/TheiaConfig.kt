package studio.forface.theia

import android.content.ContextWrapper
import io.ktor.client.HttpClient
import studio.forface.theia.TheiaParams.ScaleType.Center
import studio.forface.theia.TheiaParams.Shape.Square
import studio.forface.theia.dsl.dsl
import studio.forface.theia.log.DefaultTheiaLogger
import studio.forface.theia.log.TheiaLogger

/**
 * An object containing configuration for the library
 *
 * @author Davide Giuseppe Farella
 */
object TheiaConfig {

    /** The default [AsyncImageSource] to be used as `error` if no other value is set */
    var defaultError: ImageSource? = null

    /** The default [SyncImageSource] to be used as `placeholder` if no other value is set */
    var defaultPlaceholder: SyncImageSource? = null

    /** If `true` `error`s will respect the [TheiaParams.scaleType], else use [defaultScaleType] */
    var defaultScaleError = false

    /** If `true` `placeholder`s will respect the [TheiaParams.scaleType], else use [defaultScaleType] */
    var defaultScalePlaceholder = false

    /** The default [TheiaParams.ScaleType] to use if none is specified explicitly. Default is [Center] */
    var defaultScaleType = TheiaParams.ScaleType.Center

    /** The default [TheiaParams.Shape] to use if none is specified explicitly. Default is [Square] */
    var defaultShape = TheiaParams.Shape.Square

    /** The [HttpClient] for execute web calls */
    val httpClient = HttpClient()

    /** Whether the logging should be enabled. Default is [BuildConfig.DEBUG] */
    var loggingEnabled = BuildConfig.DEBUG

    /** A [TheiaLogger] from print messages. Default is [DefaultTheiaLogger] */
    var logger: TheiaLogger = DefaultTheiaLogger


    /* Extensions */
    var ContextWrapper.defaultPlaceholderDrawableRes: Int  by dsl { defaultPlaceholder = it.toImageSource( resources ) }
    var ContextWrapper.defaultErrorDrawableRes:       Int  by dsl { defaultError = it.toImageSource( resources ) }
}

/**
 * Invoke function for setup [TheiaConfig] within DSL style
 * E.g. >
    TheiaConfig {
        loggingEnabled = true
    }
 */
inline operator fun TheiaConfig.invoke( block: TheiaConfig.() -> Unit ) {
    block()
}

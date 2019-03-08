package studio.forface.theia

import io.ktor.client.HttpClient
import studio.forface.theia.TheiaParams.ScaleType.Center
import studio.forface.theia.TheiaParams.Shape.Square
import studio.forface.theia.log.DefaultTheiaLogger
import studio.forface.theia.log.TheiaLogger

/**
 * An object containing configuration for the library
 *
 * @author Davide Giuseppe Farella
 */
object TheiaConfig {

    /** The default [TheiaParams.Shape] to use if none is specified explicitly. Default is [Square] */
    var defaultShape = TheiaParams.Shape.Square

    /** If `true` `error`s will respect the [TheiaParams.scaleType], else use [defaultScaleType] */
    var defaultScaleError = false

    /** If `true` `placeholder`s will respect the [TheiaParams.scaleType], else use [defaultScaleType] */
    var defaultScalePlaceholder = false

    /** The default [TheiaParams.ScaleType] to use if none is specified explicitly. Default is [Center] */
    var defaultScaleType = TheiaParams.ScaleType.Center

    /** The [HttpClient] for execute web calls */
    val httpClient = HttpClient()

    /** Whether the logging should be enabled. Default is [BuildConfig.DEBUG] */
    var loggingEnabled = BuildConfig.DEBUG

    /** A [TheiaLogger] from print messages. Default is [DefaultTheiaLogger] */
    var logger: TheiaLogger = DefaultTheiaLogger
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
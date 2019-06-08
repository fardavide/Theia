@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package studio.forface.theia

import android.Manifest.permission
import android.content.ContextWrapper
import io.ktor.client.HttpClient
import studio.forface.theia.TheiaParams.ScaleType.Center
import studio.forface.theia.TheiaParams.Shape.Square
import studio.forface.theia.cache.Duration
import studio.forface.theia.cache._cleanCache
import studio.forface.theia.cache.months
import studio.forface.theia.dsl.dsl
import studio.forface.theia.log.DefaultTheiaLogger
import studio.forface.theia.log.TheiaLogger
import java.io.File

/**
 * An object containing configuration for the library
 *
 * @author Davide Giuseppe Farella
 */
object TheiaConfig {

    /**
     * A [Duration] representing how long the cached images should be used before use fresh data
     * Default is 1 month ( 1.months )
     */
    var cacheDuration = 1.months

    /**
     * The default [File] directory where to store cache.
     * Note: changing default directory may require [permission.READ_EXTERNAL_STORAGE] and
     * [permission.WRITE_EXTERNAL_STORAGE]
     *
     * Default is [initDefaultCacheDir]
     *
     * When this value is changed, cache will be removed from the old directory.
     * If new directory not [File.exists], it will be created.
     */
    var defaultCacheDirectory: File = noDirectory
        set( value ) {
            _cleanCache()
            field = value
            if ( ! field.exists() ) field.mkdir()
        }

    /** The default [AsyncImageSource] to be used as `error` if no other value is set */
    var defaultError: ImageSource? = null

    /** If `true` the Images will be transformed as `Bitmap` even if they're `Drawable` */
    var defaultForceBitmap = false

    /** The default [SyncImageSource] to be used as `placeholder` if no other value is set */
    var defaultPlaceholder: SyncImageSource? = null

    /** If `true` `error`s will respect the [TheiaParams.scaleType], else use [defaultScaleType] */
    var defaultScaleError = false

    /** If `true` `placeholder`s will respect the [TheiaParams.scaleType], else use [defaultScaleType] */
    var defaultScalePlaceholder = false

    /** The default [TheiaParams.ScaleType] to use if none is specified explicitly. Default is [Center] */
    var defaultScaleType = Center

    /** The default [TheiaParams.Shape] to use if none is specified explicitly. Default is [Square] */
    var defaultShape = Square

    /** If `true` use cache. Default is `true` */
    var defaultUseCache = true

    /** The [HttpClient] for execute web calls */
    var httpClient = HttpClient()

    /** Whether the logging should be enabled. Default is [BuildConfig.DEBUG] */
    var loggingEnabled = BuildConfig.DEBUG

    /** A [TheiaLogger] from print messages. Default is [DefaultTheiaLogger] */
    var logger: TheiaLogger = DefaultTheiaLogger


    /* Extensions */
    var ContextWrapper.defaultPlaceholderDrawableRes:   Int  by dsl { defaultPlaceholder = it.toImageSource( resources ) }
    var ContextWrapper.defaultErrorDrawableRes:         Int  by dsl { defaultError = it.toImageSource( resources ) }
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

/** An empty [File] */
internal val noDirectory = File("" )

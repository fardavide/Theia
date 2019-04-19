@file:Suppress("unused")

package studio.forface.theia

import android.content.Context
import android.content.res.Resources
import android.widget.ImageView
import androidx.core.view.doOnPreDraw
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import studio.forface.theia.AbsSyncImageSource.DrawableResImageSource
import studio.forface.theia.cache.CACHE_EXT
import studio.forface.theia.cache.Duration
import studio.forface.theia.cache._cleanCache
import studio.forface.theia.cache.mins
import studio.forface.theia.dsl.CompletionCallback
import studio.forface.theia.dsl.ErrorCallback
import studio.forface.theia.dsl.PreTargetedTheiaBuilder
import studio.forface.theia.dsl.TheiaBuilder
import studio.forface.theia.log.TheiaLogger
import java.io.File

/**
 * An interface for load images into an [ImageView]
 * Implements [CoroutineScope] for async calls.
 *
 * @author Davide Giuseppe Farella.
 */
interface ITheia: TheiaLogger, CoroutineScope {
    /** [Resources] needed for resolve [DrawableResImageSource] */
    val resources: Resources
}

/** Create a Request to apply an image into an `ImageView` */
inline fun Theia.load( builder: TheiaBuilder.() -> Unit ) {
    val params = with( TheiaBuilder( resources ) ) {
        builder()
        build()
    }
    applyParams( params )
}

/** Create a Request to apply an image into an `ImageView` from a [PreTargetedTheia] */
inline fun PreTargetedTheia.load( builder: PreTargetedTheiaBuilder.() -> Unit ) {
    val params = with( PreTargetedTheiaBuilder( resources, target ) ) {
        builder()
        build()
    }
    applyParams( params )
}

/** Middle implementation between [ITheia] and [Theia] for hide some member */
abstract class AbsTheia internal constructor(): ITheia, TheiaLogger by TheiaConfig.logger  {

    /** A [Job] for [CoroutineScope] */
    private val job = Job()

    /** @see CoroutineScope.coroutineContext */
    override val coroutineContext = job + Dispatchers.IO

    /** A list of active [AsyncRequest] */
    private val requests = mutableListOf<TheiaRequest<*>>()

    /**
     * Delete all the [File]s with extension [CACHE_EXT] with [File.lastModified] older than [olderThan] from
     * [TheiaConfig.defaultCacheDirectory]
     *
     * @param olderThan A duration for query files to delete.
     * Default is 0 minutes ( `0.mins` ), soo all the caches will be removed
     */
    fun cleanCache( olderThan: Duration = 0.mins ) = _cleanCache( olderThan )

    /** Apply [TheiaParams] */
    @PublishedApi
    internal fun applyParams( params: TheiaParams ): Unit = with( params ) {

        val load = suspend {

            // Set placeholder if image is Async
            if ( image is Async ) load( placeholder, scalePlaceholder )

            // Set image and handle error
            // On completion call params.completionCallback
            load( image, onCompletion = params.completionCallback ) {

                // Call errorCallback if not null.
                params.errorCallback?.invoke( it )

                // Set placeholder if error is NOT Sync ( Async or null )
                if ( error !is Sync ) load( placeholder, scalePlaceholder )

                // Set error image. On error, fallback on placeholder again
                load( error, scaleError ) { load( placeholder, scalePlaceholder ) }
            }
        }

        fun launchLoad() = launch { load() }

        // If we have Dimensions, load now, else on target's pre-draw
        when {
            params.dimensions != null -> launchLoad()
            params.target != null -> params.target.doOnPreDraw { launchLoad() }
            else -> throw AssertionError()
        }
    }

    /** Apply [ImageSource] with its [TheiaParams] */
    private suspend fun applySource(
        source: ImageSource?,
        params: TheiaParams,
        applyScale: Boolean = true,
        onCompletion: CompletionCallback,
        onError: ErrorCallback
    ) {
        val requestParams = RequestParams of params

        // Create the request
        val request = when( source ) {
            is Async ->
                AsyncRequest(
                    TheiaConfig.httpClient,
                    requestParams
                )

            is Sync -> SyncRequest( requestParams )

            null -> return
        }

        // Append the request
        appendRequest( request )

        // Invoke the request
        request(
            source,
            overrideScaleType = if ( ! applyScale ) TheiaConfig.defaultScaleType else null,
            onComplete = {
                dropRequest( request )
                onCompletion( it ) // Deliver to CompletionCallback
                params.target?.setImageBitmap( it ) // Set into target
            },
            onError = {
                dropRequest( request )
                onError( it ) // Deliver ErrorCallback
                if ( it.isFatal ) error( it )
                else info( it )
            }
        )
    }

    /** Append a new [TheiaRequest] to [requests] */
    private fun appendRequest( request: Request ) {
        requests.append( request )
    }

    /** Stop and remove a [TheiaRequest] from [request] */
    private fun dropRequest( request: Request ) {
        ( request as? AsyncRequest )?.let {
            requests -= it
        }
    }

    /** Purge all the [requests] */
    internal open fun purgeRequests() {
        requests.purge()
    }

    /** Stop all the [TheiaRequest] and [clear] */
    private fun MutableList<Request>.append( request: Request ) {
        this += request
    }

    /** Stop all the [TheiaRequest] and [clear] */
    private fun MutableList<Request>.purge() {
        job.cancel()
        this.clear()
    }

    /** Call [load] within [TheiaParams] */
    private suspend fun TheiaParams.load(
        source: ImageSource?,
        applyScale: Boolean = true,
        onCompletion: CompletionCallback? = {},
        onError: ErrorCallback = {}
    ) {
        applySource( source,this, applyScale, onCompletion ?: {}, onError )
    }
}


/** Default implementation of [ITheia] */
open class Theia internal constructor( override val resources: Resources ) : AbsTheia()

/** Implementation of [ITheia] with a pre-targeted [ImageView] */
class PreTargetedTheia internal constructor (
    override val resources: Resources,
    val target: ImageView
) : AbsTheia()

/** Implementation of [Theia] that exposes [purgeRequests] publicly */
class UnhandledTheia internal constructor( resources: Resources ) : Theia( resources ) {
    public override fun purgeRequests() {
        super.purgeRequests()
    }
}


/** A typealias for [TheiaRequest] without generic */
private typealias Request = TheiaRequest<*>


/** Initialize [TheiaConfig.defaultCacheDirectory] if not set yet */
internal fun initDefaultCacheDir( context: Context ) {
    if ( TheiaConfig.defaultCacheDirectory == noDirectory )
        TheiaConfig.defaultCacheDirectory = context.cacheDir
}
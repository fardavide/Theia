package studio.forface.theia

import android.graphics.Bitmap
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import studio.forface.theia.AbsAsyncImageSource.*
import studio.forface.theia.AbsSyncImageSource.*
import studio.forface.theia.TheiaConfig.cacheDuration
import studio.forface.theia.TheiaParams.ScaleType
import studio.forface.theia.cache.get
import studio.forface.theia.cache.set
import studio.forface.theia.log.TheiaLogger
import studio.forface.theia.utils.*
import java.io.ByteArrayOutputStream
import java.io.File
import kotlin.coroutines.CoroutineContext
import studio.forface.theia.cache.minus
import java.lang.System.currentTimeMillis

/**
 * A request for retrieve and apply the image.
 *
 * Implements [CoroutineScope] for async calls.
 *
 * @author Davide Giuseppe Farella.
 */
internal abstract class TheiaRequest<in ImageSource: AbsImageSource<*>> :
    CoroutineScope, TheiaLogger by TheiaConfig.logger {

    /** A [Job] for [CoroutineScope] */
    private val job = Job()

    /** @see CoroutineScope.coroutineContext */
    override val coroutineContext = Main + job

    /** The directory for caches */
    private val cacheDirectory get() = TheiaConfig.defaultCacheDirectory

    /** The [CoroutineContext] for [getBitmap] */
    abstract val callContext: CoroutineContext

    /** The [RequestParams] */
    abstract val params: RequestParams

    /**
     * Invoke the request with the given [ImageSource]
     *
     * @param onComplete a lambda that receives the processed [Bitmap]
     * @param onError a lambda that receives the generated [TheiaException]
     */
    operator fun invoke(
        source: ImageSource,
        overrideScaleType: ScaleType? = null,
        onComplete: (Bitmap) -> Unit = {},
        onError: (TheiaException) -> Unit = {}
    ) {
        launch {
            runCatching { handleSource( source ) }
                .mapCatching { prepareBitmap( it, overrideScaleType ) }
                .onSuccess { onComplete( it ) }
                .onFailure { onError( it.toTheiaException() ) }
        }
    }

    /**
     * @return [Bitmap]
     * Try to get [Bitmap] from cache, if null get from [ImageSource] on [callContext] and then store it in cache
     */
    private suspend fun handleSource( source: ImageSource ) = coroutineScope {
        return@coroutineScope getCachedBitmap( source ) ?:
                withContext( callContext ) { getBitmap( source ) }
                    .also { storeInCache( it, source ) }
    }

    /** @return a [Bitmap] get from the given [ImageSource] */
    abstract suspend fun getBitmap( source: ImageSource ): Bitmap

    /**
     * @return a [Bitmap] stored in cache from the given [ImageSource]
     * @return null if [params] has [RequestParams.useCache], [AbsImageSource.isLocalSource], [canUseCache] is false or
     * if cache is not available.
     */
    private fun getCachedBitmap( source: ImageSource ): Bitmap? {
        if ( ! params.useCache || source.isLocalSource || ! canUseCache() ) return null
        return cacheDirectory[source.cacheName, currentTimeMillis() - cacheDuration]?.readBytes()?.toBitmap()
    }

    /**
     * Store the given [Bitmap] in cache, if:
     * * [params] has [RequestParams.useCache]
     * * [AbsImageSource.isRemoteSource]
     * * [canUseCache]
     * * cache doesn't exist for this [ImageSource]
     */
    private fun storeInCache( bitmap: Bitmap, source: ImageSource ) {
        if ( ! params.useCache || source.isLocalSource || ! canUseCache() ) return
        if ( cacheDirectory[source.cacheName] != null ) return
        val stream = ByteArrayOutputStream()
        bitmap.compress( Bitmap.CompressFormat.PNG, 100, stream )
        cacheDirectory[source.cacheName] = stream.toByteArray()
    }

    /**
     * @return `true` if [TheiaConfig.defaultCacheDirectory] [File.canWrite], else false and log
     * [MissingCacheStoragePermissionsException]
     */
    private fun canUseCache() = cacheDirectory.canWrite()
        .also { allowed -> if ( ! allowed ) error( MissingCacheStoragePermissionsException() ) }

    /** Apply the needed transformations to the given [Bitmap] */
    private fun prepareBitmap(
        bitmap: Bitmap,
        overrideScaleType: ScaleType? = null
    ): Bitmap = with( params ) {
        bitmap
            .applyDimensions( dimensions,overrideScaleType ?: scaleType )
            .applyTransformation( shape.transformation )
            .applyTransformations( extraTransformations )
    }

    /** Stop the current Request */
    fun stop() {
        job.cancel()
    }
}

/** A [TheiaRequest] that will be executed synchronously */
internal class SyncRequest( override val params: RequestParams ): TheiaRequest<SyncImageSource>() {

    /** The [CoroutineContext] for [getBitmap] */
    override val callContext = Main

    /** @return a [Bitmap] get from the given [ImageSource] */
    override suspend fun getBitmap( source: SyncImageSource ): Bitmap {
        return when ( source ) {
            is BitmapImageSource -> source.source
            is DrawableImageSource -> source.source.toHighResBitmap()
            is DrawableResImageSource -> source.resolveDrawable().toHighResBitmap()
            is FileImageSource -> source.source.readBytes().toBitmap()
        }
    }
}

/** A [TheiaRequest] that will be executed asynchronously */
internal class AsyncRequest(
    private val client: HttpClient,
    override val params: RequestParams
): TheiaRequest<AsyncImageSource>() {

    /** The [CoroutineContext] for [getBitmap] */
    override val callContext = IO

    /** @return a [Bitmap] get from the given [ImageSource] */
    override suspend fun getBitmap( source: AsyncImageSource ): Bitmap {
        return when ( source ) {
            is AsyncFileImageSource -> source.source.readBytes()
            is StringImageSource -> client.get( source.source )
            is UrlImageSource -> source.source.readBytes()
        }.toBitmap()
    }
}
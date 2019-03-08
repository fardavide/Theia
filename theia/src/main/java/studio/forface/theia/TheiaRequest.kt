package studio.forface.theia

import android.graphics.Bitmap
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import studio.forface.theia.AbsAsyncImageSource.*
import studio.forface.theia.AbsSyncImageSource.*
import studio.forface.theia.TheiaParams.ScaleType
import studio.forface.theia.utils.*
import kotlin.coroutines.CoroutineContext

/**
 * A request for retrieve and apply the image.
 *
 * Implements [CoroutineScope] for async calls.
 *
 * @author Davide Giuseppe Farella.
 */
internal abstract class TheiaRequest<in ImageSource> : CoroutineScope {

    /** A [Job] for [CoroutineScope] */
    private val job = Job()

    /** @see CoroutineScope.coroutineContext */
    override val coroutineContext = Main + job

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

    /** @return a [Bitmap] stored in cache from the given [ImageSource] */
    private fun getCachedBitmap( source: ImageSource ): Bitmap? = null //TODO Cache not implemented

    /** Store the given [Bitmap] in cache */
    private fun storeInCache( bitmap: Bitmap, source: ImageSource ) {} //TODO Cache not implemented

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
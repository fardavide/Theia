@file:Suppress("unused")

package studio.forface.theia

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import studio.forface.theia.AbsAsyncImageSource.*
import studio.forface.theia.AbsSyncImageSource.*
import studio.forface.theia.cache.CACHE_EXT
import studio.forface.theia.utils.getDrawableCompat
import java.io.File
import java.net.URL

/**
 * A sealed class that wraps the source of the image to load.
 *
 * @param Type the type of [source]
 *
 * @author Davide Giuseppe Farella
 */
sealed class AbsImageSource<out Type> {
    internal abstract val source: Type
    internal val cacheName get() = "$source$CACHE_EXT".replace( File.separatorChar, '.' )
    internal open val isRemoteSource = false
    internal val isLocalSource get() = ! isRemoteSource
}

/** A sealed class for [ImageSource] that will be loaded synchronously */
sealed class AbsSyncImageSource<out Type> : AbsImageSource<Type>() {

    /** A [SyncImageSource] of type [Bitmap] */
    class BitmapImageSource( override val source: Bitmap ) : AbsSyncImageSource<Bitmap>()

    /** A [SyncImageSource] of type [Drawable] */
    class DrawableImageSource( override val source: Drawable ) : AbsSyncImageSource<Drawable>()

    /** A [SyncImageSource] of type [Int] [DrawableRes] */
    class DrawableResImageSource(
        @DrawableRes override val source: Int,
        private val context: Context
    ) : AbsSyncImageSource<Int>() {

        /** @return a [Drawable] from [source] */
        fun resolveDrawable(): Drawable = context.getDrawableCompat( source )
    }

    /** A [SyncImageSource] of type [File] */
    class FileImageSource( override val source: File ) : AbsSyncImageSource<File>()
}

/** A sealed class for [ImageSource] that will be loaded asynchronously */
sealed class AbsAsyncImageSource<out Type> : AbsImageSource<Type>() {

    /** An [AsyncImageSource] of type [File] */
    class AsyncFileImageSource( override val source: File ) : AbsAsyncImageSource<File>()

    /** An [AsyncImageSource] of type [String] */
    class StringImageSource( override val source: String ) : AbsAsyncImageSource<String>() {
        override val isRemoteSource = true
    }

    /** An [AsyncImageSource] of type [URL] */
    class UrlImageSource( override val source: URL ) : AbsAsyncImageSource<URL>() {
        override val isRemoteSource = true
    }
}


/** Typealias for [AbsImageSource] of any type */
typealias ImageSource = AbsImageSource<*>

/** Typealias for [AbsSyncImageSource] of any type */
typealias SyncImageSource = AbsSyncImageSource<*>

/**
 * Typealias for [SyncImageSource] for brevity purpose of checks
 * E.g. `if ( imageSource is Sync ) doSomething()`
 */
internal typealias Sync = SyncImageSource

/** Typealias for [AbsAsyncImageSource] of any type */
typealias AsyncImageSource = AbsAsyncImageSource<*>

/**
 * Typealias for [AsyncImageSource] for brevity purpose of checks
 * E.g. `if ( imageSource is Async ) doSomething()`
 */
internal typealias Async = AsyncImageSource


/** @return a [BitmapImageSource] from [Bitmap] */
fun Bitmap.toImageSource() = BitmapImageSource( this )

/** @return a [DrawableImageSource] from [Drawable] */
fun Drawable.toImageSource() = DrawableImageSource( this )

/** @return a [DrawableResImageSource] from [Int] [DrawableRes] */
fun @receiver: DrawableRes Int.toImageSource( context: Context ) = DrawableResImageSource( this, context )

/** @return a [FileImageSource] from [File] */
fun File.toImageSource() = FileImageSource( this )

/** @return a [AsyncFileImageSource] from [File] */
fun File.toAsyncImageSource() = AsyncFileImageSource( this )

/** @return a [StringImageSource] from [String] */
fun String.toImageSource() = StringImageSource( this )

/** @return a [UrlImageSource] from [URL] */
fun URL.toImageSource() = UrlImageSource( this )
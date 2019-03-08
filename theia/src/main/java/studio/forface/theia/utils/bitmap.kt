package studio.forface.theia.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.graphics.createBitmap
import androidx.core.graphics.drawable.toBitmap

/**
 * @return a [Bitmap] created by cropping by the given [newWidth] [newHeight]
 * If [Bitmap.getWidth] < than [newWidth] or [Bitmap.getHeight] < [newHeight], apply a transparent background to fill
 * the sizes.
 */
internal fun Bitmap.forceCrop( newWidth: Int, newHeight: Int ): Bitmap {
    val x = ( newWidth - width ) / 2
    val y = ( newHeight - height ) / 2
    val result = createBitmap( newWidth, newHeight )
    val canvas = Canvas( result )
    canvas.drawBitmap(this, x.toFloat(), y.toFloat(),null )
    return result
}

/** @return a [Bitmap] created from the given [ByteArray] */
internal fun ByteArray.toBitmap() = BitmapFactory.decodeByteArray(this, 0, this.size )

/** @return a `512 x 512` [Bitmap] from a [Drawable] */
internal fun Drawable.toHighResBitmap() = toBitmap(512,512 )
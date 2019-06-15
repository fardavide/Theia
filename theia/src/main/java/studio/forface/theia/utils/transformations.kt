@file:Suppress("UNUSED_PARAMETER") // TODO not implemented methods

package studio.forface.theia.utils

import android.content.res.Resources
import android.graphics.*
import androidx.core.graphics.scale
import studio.forface.theia.*
import studio.forface.theia.imageManipulation.TheiaTransformer
import studio.forface.theia.transformation.TheiaTransformation
import studio.forface.theia.utils.Use.Height
import studio.forface.theia.utils.Use.Width
import kotlin.math.absoluteValue


/** Apply a [Dimensions] to a [TheiaResponse] */
internal fun TheiaResponse.applyDimensions( dimensions: Dimensions, scaleType: TheiaParams.ScaleType ): TheiaResponse {
    return with( TheiaTransformer ) {
        when (scaleType) {
            TheiaParams.ScaleType.Center ->     center( dimensions )
            TheiaParams.ScaleType.Crop ->       crop( dimensions )
            TheiaParams.ScaleType.Fit ->        fit( dimensions )
            TheiaParams.ScaleType.Stretch ->    stretch( dimensions )
        }
    }
}

/** Apply a [TheiaTransformation] to a [TheiaResponse] */
internal fun TheiaResponse.applyTransformation( transformation: TheiaTransformation ) =
    transformation.invoke(this )

/** Apply a [Collection] of [TheiaTransformation] to a [TheiaResponse] */
internal fun TheiaResponse.applyTransformations(
    transformations: Collection<TheiaTransformation>
): TheiaResponse {
    var response = this
    transformations.forEach { transformation -> response = transformation( response ) }
    return response
}

// region Bitmap
/**
 * @return a [Bitmap] resized to be centered inside the given [Dimensions]
 * @see TheiaParams.ScaleType.Center
 */
internal fun Bitmap.centerTo( dimensions: Dimensions ): Bitmap {
    val ratio = width.toDouble() / height
    val dimRation = dimensions.width.toDouble() / dimensions.height

    // If bitmap has an higher ratio than Dimensions', that means its larger, so we use `Width`, else use `Height`
    val use = if ( ratio > dimRation ) Width else /* ratio <= dimRation */ Height
    val ( late, dimLate ) = when( use ) {
        Width -> width to dimensions.width
        Height -> height to dimensions.height
    }
    val scaleRatio = dimLate.toDouble() / late
    return scale( this.dimensions * scaleRatio )
}

/**
 * @return a [Bitmap] cropped to the given [Dimensions]
 * @see forceCrop
 * @see TheiaParams.ScaleType.Crop
 */
internal fun Bitmap.cropTo( dimensions: Dimensions ) = forceCrop( dimensions.width, dimensions.height )

/**
 * @return a [Bitmap] resized to fit the given [Dimensions]
 * @see TheiaParams.ScaleType.Fit
 */
internal fun Bitmap.fitTo( dimensions: Dimensions ): Bitmap {
    val ratio = width.toDouble() / height
    val dimRation = dimensions.width.toDouble() / dimensions.height

    // If bitmap has an higher ratio than Dimensions', that means its larger, so we use `Height`, else use `Width`
    val use = if ( ratio > dimRation ) Height else /* ratio <= dimRation */ Width
    val ( late, dimLate ) = when( use ) {
        Width -> width to dimensions.width
        Height -> height to dimensions.height
    }
    val scaleRatio = dimLate.toDouble() / late
    return scale( this.dimensions * scaleRatio ).cropTo( dimensions )
}

/**
 * @return a [Bitmap] stretched to fill the given [Dimensions]
 * @see TheiaParams.ScaleType.Stretch
 */
internal fun Bitmap.stretchTo( dimensions: Dimensions ) = scale( dimensions )

/** @return a circle [Bitmap] */
internal fun Bitmap.toCircle() : Bitmap {
    val size = Math.min( width, height )

    // Calculate the center of the image
    val x = ( width - size ) / 2
    val y = ( height - size ) / 2

    val squaredBitmap = Bitmap.createBitmap( this, x, y, size, size )
    val bitmap = Bitmap.createBitmap( size, size, config )

    if ( squaredBitmap != this ) recycle()

    val canvas = Canvas( bitmap )
    val paint = Paint()
    val shader = BitmapShader( squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP )
    paint.shader = shader
    paint.isAntiAlias = true

    val r = size / 2f
    canvas.drawCircle( r, r, r, paint )

    squaredBitmap.recycle()
    return bitmap
}

/** @return a [Bitmap] with rounded corners */
internal fun Bitmap.roundTo( corners: Corners ) : Bitmap {
    val largerSize = Math.max( width, height )
    val cornerPixelRadius = when ( corners ) {
        is ImageCorners.Pixel -> corners.number
        is ImageCorners.Dp -> ( corners.number * Resources.getSystem().displayMetrics.density ).toInt()
        is ImageCorners.Percentage -> {
            val percentage = with( corners.number.absoluteValue ) { if ( this > 1 ) this / 100 else this }
            val largerR = largerSize / 2
            ( largerR * percentage ).toInt()
        }
    }

    val bitmap = Bitmap.createBitmap( width, height, config )

    val clipPath = Path()
    val canvas = Canvas( bitmap )

    val rect = RectF(0f, 0f, width.toFloat(), height.toFloat() )
    val r = cornerPixelRadius.toFloat()
    clipPath.addRoundRect( rect, r, r, Path.Direction.CW )
    canvas.clipPath( clipPath )

    val paint = Paint()
    val shader = BitmapShader( bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP )
    paint.shader = shader
    paint.isAntiAlias = true

    canvas.drawBitmap( this, 0f, 0f, paint )

    recycle()

    return bitmap
}

/** Scale a [Bitmap] with the given [Dimensions] */
private fun Bitmap.scale( dimensions: Dimensions ) = scale( dimensions.width, dimensions.height )
// endregion

/** An enum used by [centerTo] and [fitTo] to readability purpose */
private enum class Use { Width, Height }
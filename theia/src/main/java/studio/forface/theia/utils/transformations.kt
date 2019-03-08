package studio.forface.theia.utils

import android.graphics.Bitmap
import androidx.core.graphics.scale
import studio.forface.theia.Dimensions
import studio.forface.theia.TheiaParams
import studio.forface.theia.height
import studio.forface.theia.transformation.TheiaTransformation
import studio.forface.theia.utils.Use.Height
import studio.forface.theia.utils.Use.Width
import studio.forface.theia.width

/** Apply a [Dimensions] to a [Bitmap] */
internal fun Bitmap.applyDimensions( dimensions: Dimensions, scaleType: TheiaParams.ScaleType ): Bitmap {
    return when( scaleType ) {
        TheiaParams.ScaleType.Center ->   centerTo( dimensions )
        TheiaParams.ScaleType.Crop ->     cropTo( dimensions )
        TheiaParams.ScaleType.Fit ->      fitTo( dimensions )
        TheiaParams.ScaleType.Stretch ->  stretchTo( dimensions )
    }
}

/** Apply a [TheiaTransformation] to a [Bitmap] */
internal fun Bitmap.applyTransformation( transformation: TheiaTransformation ) =
    transformation.invoke(this )

/** Apply a [Collection] of [TheiaTransformation] to a [Bitmap] */
internal fun Bitmap.applyTransformations( transformations: Collection<TheiaTransformation> ): Bitmap {
    var bitmap = this
    transformations.forEach { transformation -> bitmap = transformation( bitmap ) }
    return bitmap
}

/**
 * @return a [Bitmap] resized to be centered inside the given [Dimensions]
 * @see TheiaParams.ScaleType.Center
 */
internal fun Bitmap.centerTo( dimensions: Dimensions ): Bitmap {
    val ratio = width.toDouble() / height
    val dimRation = dimensions.width.toDouble() / dimensions.height

    // If bitmap has an higher ratio than Dimensions', that means its larger, so we use `Width`, else use `Height`
    val use = if ( ratio > dimRation ) Use.Width else /* ratio <= dimRation */ Use.Height
    val ( late, dimLate ) = when( use ) {
        Width -> width to dimensions.width
        Height -> height to dimensions.height
    }
    val scaleRatio = dimLate.toDouble() / late
    return scale( ( width * scaleRatio ).toInt(), ( height * scaleRatio  ).toInt())
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
    val use = if ( ratio > dimRation ) Use.Height else /* ratio <= dimRation */ Use.Width
    val ( late, dimLate ) = when( use ) {
        Width -> width to dimensions.width
        Height -> height to dimensions.height
    }
    val scaleRatio = dimLate.toDouble() / late
    return scale( ( width * scaleRatio ).toInt(), ( height * scaleRatio ).toInt() ).cropTo( dimensions )
}

/**
 * @return a [Bitmap] stretched to fill the given [Dimensions]
 * @see TheiaParams.ScaleType.Stretch
 */
internal fun Bitmap.stretchTo( dimensions: Dimensions ) = scale( dimensions.width, dimensions.height )


/** An enum used by [centerTo] and [fitTo] to readability purpose */
private enum class Use { Width, Height }
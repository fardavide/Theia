@file:Suppress("UNUSED_PARAMETER") // TODO not implemented methods

package studio.forface.theia.utils

import android.graphics.Bitmap
import androidx.core.graphics.scale
import studio.forface.theia.*
import studio.forface.theia.imageManipulation.TheiaTransformer
import studio.forface.theia.transformation.TheiaTransformation
import studio.forface.theia.utils.Use.Height
import studio.forface.theia.utils.Use.Width

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

/** Scale a [Bitmap] with the given [Dimensions] */
private fun Bitmap.scale( dimensions: Dimensions ) = scale( dimensions.width, dimensions.height )
// endregion

/** An enum used by [centerTo] and [fitTo] to readability purpose */
private enum class Use { Width, Height }
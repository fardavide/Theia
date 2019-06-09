package studio.forface.theia

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import studio.forface.theia.transformation.TheiaTransformation

/**
 * Params for [TheiaRequest]
 *
 * @author Davide Giuseppe Farella
 */
internal data class RequestParams(
    val dimensions: Dimensions,
    val scaleType: TheiaParams.ScaleType,
    val shape: TheiaParams.Shape,
    val extraTransformations: List<TheiaTransformation>,
    val useCache: Boolean,
    val forceBitmap: Boolean
) {
    companion object {

        /** @return [RequestParams] created from [TheiaParams] */
        infix fun of( theiaParams: TheiaParams ) = with( theiaParams ) {
            RequestParams(
                dimensions =            dimensions ?: target?.dimensions ?: throw AssertionError(),
                scaleType =             scaleType,
                shape =                 shape,
                extraTransformations =  extraTransformations,
                useCache =              useCache,
                forceBitmap =           forceBitmap
            )
        }
    }
}

/** A typealias for a [Pair] of [Int]s */
typealias Dimensions = Pair<Int, Int>
internal val Dimensions.width get() = first
internal val Dimensions.height get() = second

/** [times] operator for scale the receiver [Dimensions] */
internal operator fun Dimensions.times( scaleFactor: Double ) = scale( scaleFactor )

/** [times] operator for scale the receiver [Dimensions] if not `null` */
@JvmName("nullableTimes" )
internal operator fun Dimensions?.times( scaleFactor: Double ) = scale( scaleFactor )

/** Scale [width] and [height] of the receiver [Dimensions] */
internal fun Dimensions.scale( scaleFactor: Double ) : Dimensions =
    ( width * scaleFactor ).toInt() to ( height * scaleFactor ).toInt()

/** Scale [width] and [height] of the receiver [Dimensions] if not `null` */
@JvmName("nullableScale" )
internal fun Dimensions?.scale( scaleFactor: Double ) = this?.scale( scaleFactor )


/** @return a [Dimensions] from [ImageView] */
internal val ImageView.dimensions : Dimensions get() = width to height

/** @return a [Dimensions] from [Bitmap] */
internal val Bitmap.dimensions : Dimensions get() = width to height

/** @return a [Dimensions] from [Drawable] if it has intrinsic, else `null` */
internal val Drawable.dimensions : Dimensions? get() {
    val dimensions = intrinsicWidth to intrinsicHeight
    return if ( dimensions.width > 0 && dimensions.height > 0 ) dimensions
    else null
}
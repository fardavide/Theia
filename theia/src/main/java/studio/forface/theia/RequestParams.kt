package studio.forface.theia

import android.widget.ImageView
import studio.forface.theia.transformation.TheiaTransformation

/**
 * Params for [TheiaRequest]
 *
 * @author Davide Giuseppe Farella
 */
internal data class RequestParams(
    internal val dimensions: Dimensions,
    internal val scaleType: TheiaParams.ScaleType,
    internal val shape: TheiaParams.Shape,
    internal val extraTransformations: List<TheiaTransformation>,
    internal val useCache: Boolean
) {
    companion object {

        /** @return [RequestParams] created from [TheiaParams] */
        infix fun of( theiaParams: TheiaParams ) = with( theiaParams ) {
            RequestParams(
                dimensions =            dimensions ?: target?.dimensions ?: throw AssertionError(),
                scaleType =             scaleType,
                shape =                 shape,
                extraTransformations =  extraTransformations,
                useCache =              useCache
            )
        }
    }
}

/** A typealias for a [Pair] of [Int]s */
typealias Dimensions = Pair<Int, Int>
internal val Dimensions.width get() = first
internal val Dimensions.height get() = second

/** @return a [Dimensions] from [ImageView] */
internal val ImageView.dimensions get() = width to height
package studio.forface.theia

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
    internal val extraTransformations: List<TheiaTransformation>
) {
    companion object {

        /** @return [RequestParams] created from [TheiaParams] */
        infix fun of( theiaParams: TheiaParams ) = with( theiaParams ) {
            RequestParams(
                dimensions =            target.width to target.height,
                scaleType =             scaleType,
                shape =                 shape,
                extraTransformations =  extraTransformations
            )
        }
    }
}

/** A typealias for a [Pair] of [Int]s */
internal typealias Dimensions = Pair<Int, Int>
internal val Dimensions.width get() = first
internal val Dimensions.height get() = second
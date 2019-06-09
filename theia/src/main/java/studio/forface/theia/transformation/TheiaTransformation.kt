package studio.forface.theia.transformation

import studio.forface.theia.TheiaResponse

/**
 * An interface for apply a transformation to a [TheiaResponse]
 *
 * @author Davide Giuseppe Farella
 */
interface TheiaTransformation {

    /** @return the transformed [TheiaResponse] */
    operator fun invoke( source: TheiaResponse ): TheiaResponse
}

/** An empty [TheiaTransformation] that return the unchanged source [TheiaResponse] */
internal object NoTransformation : TheiaTransformation {
    override fun invoke( source: TheiaResponse ) = source
}
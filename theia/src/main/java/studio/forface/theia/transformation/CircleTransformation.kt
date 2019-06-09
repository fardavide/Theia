package studio.forface.theia.transformation

import studio.forface.theia.TheiaResponse
import studio.forface.theia.imageManipulation.transform

/**
 * A [TheiaTransformation] for crate a round Image
 *
 * @author Davide Giuseppe Farella
 */
object CircleTransformation : TheiaTransformation {

    override fun invoke( source: TheiaResponse ) = source.transform { it.round() }
}
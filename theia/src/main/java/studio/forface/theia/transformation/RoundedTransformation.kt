package studio.forface.theia.transformation

import studio.forface.theia.Corners
import studio.forface.theia.TheiaResponse
import studio.forface.theia.imageManipulation.transform

/**
 * A [TheiaTransformation] for crate a rounded corners Image
 * @author Davide Giuseppe Farella
 */
class RoundedTransformation( private val corners: Corners ) : TheiaTransformation {

    override fun invoke( source: TheiaResponse ) = source.transform { it.round( corners ) }
}
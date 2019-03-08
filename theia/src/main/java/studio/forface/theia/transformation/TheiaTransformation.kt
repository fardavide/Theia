package studio.forface.theia.transformation

import android.graphics.Bitmap

/**
 * An interface for apply a transformation to a [Bitmap]
 *
 * @author Davide Giuseppe Farella
 */
interface TheiaTransformation {

    /** @return the transformed [Bitmap] */
    operator fun invoke( source: Bitmap ): Bitmap
}

/** An empty [TheiaTransformation] that return the unchanged source [Bitmap] */
internal object NoTransformation : TheiaTransformation {
    override fun invoke( source: Bitmap ) = source
}
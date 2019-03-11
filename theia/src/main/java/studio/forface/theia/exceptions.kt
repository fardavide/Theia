package studio.forface.theia

import studio.forface.theia.dsl.TheiaBuilder
import kotlinx.coroutines.CancellationException

/**
 * A base [Exception] for the library
 *
 * @author Davide Giuseppe Farella
 */
open class TheiaException : Exception {

    /** @constructor for class that inherit [TheiaException] */
    constructor( message: String = "Generic $THEIA_NAME exception", isFatal: Boolean = true ): super( message ) {
        this.isFatal = isFatal
    }

    /** @constructor for wrap "unknown" [Throwable]s */
    constructor( cause: Throwable, isFatal: Boolean = true ) : super( cause ) {
        this.isFatal = isFatal
    }

    /** @return non-null [String] [Throwable.message] or empty [String] */
    override val message: String get() = super.message ?: ""

    /** A [Boolean] representing whether an error is fatal */
    val isFatal: Boolean
}

/** @return NOT null [String] [TheiaException.getLocalizedMessage] or [TheiaException.message] if first one is null */
val TheiaException.actualMessage: String get() = localizedMessage ?: message


/** [TheiaException] describing that a Request has been cancelled */
class CancelledRequestException : TheiaException( "Request has been cancelled", isFatal = false )

/** [TheiaException] describing that no [TheiaBuilder.image] or [TheiaBuilder.placeholder] has been set */
class ImageSourceNotSetException : TheiaException( "Both 'image' and 'placeholder' are null" )

/** [TheiaException] describing that no permissions have been provided for use cache */
class MissingCacheStoragePermissionsException : TheiaException(
    "Impossible to use cache due to missing storage permissions. Please provide 'READ_EXTERNAL_STORAGE' and " +
            "'WRITE_EXTERNAL_STORAGE' permissions"
)

/**
 * [TheiaException] describing that the Request is useless since none of [TheiaBuilder.target] and
 * [TheiaBuilder.completionCallback] is supplied
 */
class PointlessRequestException :
        TheiaException( "The request is useless since both 'target' and 'completionCallback' are null" )

/** [TheiaException] describing that both [TheiaBuilder.target] and [TheiaBuilder.dimensions] are null */
class UndefinedDimensionsException : TheiaException( "If no 'target' is declared, 'dimensions' should be defined" )


/** @return a [TheiaException] generated from the given [Throwable] */
internal fun Throwable.toTheiaException() = when( this ) {
    is CancellationException -> CancelledRequestException()
    else -> TheiaException( cause = this )
}
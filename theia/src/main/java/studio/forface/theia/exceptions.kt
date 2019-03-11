package studio.forface.theia

import studio.forface.theia.dsl.TheiaBuilder

/**
 * A base [Exception] for the library
 *
 * @author Davide Giuseppe Farella
 */
open class TheiaException(
    override val message: String = "Generic $THEIA_NAME exception",
    override val cause: Throwable? = null
) : Exception()

/** @return NOT null [String] [TheiaException.getLocalizedMessage] or [TheiaException.message] if first one is null */
val TheiaException.actualMessage: String get() = localizedMessage ?: message


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

/** [TheiaException] describing that no [TheiaBuilder.target] has been set */
@Deprecated( "This exception is not used anymore, since target is optional if a CompletionCallback is supplied" ) // TODO remove in 0.3
class TargetNotSetException : TheiaException( "'target' is required! No 'target' set" )

/** [TheiaException] describing that both [TheiaBuilder.target] and [TheiaBuilder.dimensions] are null */
class UndefinedDimensionsException : TheiaException( "If no 'target' is declared, 'dimensions' should be defined" )


/** @return a [TheiaException] generated from the given [Throwable] */
internal fun Throwable.toTheiaException() = TheiaException( message ?: "", cause ) // TODO: stacktrace
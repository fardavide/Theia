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

/** [TheiaException] describing that no [TheiaBuilder.image] or [TheiaBuilder.placeholder] has been set */
class ImageSourceNotSetException : TheiaException( "Both 'image' and 'placeholder' are null" )

/** [TheiaException] describing that no [TheiaBuilder.target] has been set */
class TargetNotSetException : TheiaException( "'target' is required! No 'target' set" )


/** @return NOT null [String] [TheiaException.getLocalizedMessage] or [TheiaException.message] if first one is null */
val TheiaException.actualMessage: String get() = localizedMessage ?: message

/** @return a [TheiaException] generated from the given [Throwable] */
internal fun Throwable.toTheiaException()= TheiaException( message ?: "", cause )
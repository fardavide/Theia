package studio.forface.theia

import kotlinx.coroutines.CancellationException
import studio.forface.theia.dsl.TheiaBuilder
import studio.forface.theia.imageManipulation.TransformationPipeline

/**
 * A base [Exception] for the library
 *
 * @author Davide Giuseppe Farella
 */
open class TheiaException : Exception {

    /** @constructor for class that inherit [TheiaException] */
    internal constructor( message: String = "Generic $THEIA_NAME exception", isFatal: Boolean = true ): super( message ) {
        this.isFatal = isFatal
    }

    /** @constructor for wrap "unknown" [Throwable]s */
    internal constructor( cause: Throwable, isFatal: Boolean = true ) : super( cause ) {
        this.isFatal = isFatal
    }

    /** @return non-null [String] [Throwable.message] or empty [String] */
    override val message: String get() = super.message ?: ""

    /** A [Boolean] representing whether an error is fatal */
    val isFatal: Boolean
}

/** @return NOT null [String] [TheiaException.getLocalizedMessage] or [TheiaException.message] if first one is null */
val TheiaException.actualMessage: String get() = localizedMessage ?: message

/** Return the [String] name of the actual class of a [ClassCastException] */
internal val ClassCastException.actualClassName get() = message?.split(" ")?.first()


/** [TheiaException] describing that a Request has been cancelled */
class CancelledRequestException internal constructor() : TheiaException( "Request has been cancelled", isFatal = false )

/** [TheiaException] describing that Transformations are not supported yet on `Drawables` */
class DrawableTransformationNotSupportedException internal constructor() : TheiaException(
    "Transformations are not supported on `Drawable`s yet, consider using `TheiaParams.forceBitmap`"
)

/** [TheiaException] describing that a [TransformationPipeline] has been called on an illegal receiver */
class IllegalPipelineReceiverException @PublishedApi internal constructor( e: ClassCastException ) : TheiaException(
    "Pipeline has been called on '${e.actualClassName}'. You may have forgot to use the argument from `fromImage`," +
            "for example `transformImage { center( dimensions ) }` instead of `transformImage { it.center( dimensions ) }`"
)

/** [TheiaException] describing that no [TheiaBuilder.image] or [TheiaBuilder.placeholder] has been set */
class ImageSourceNotSetException internal constructor() : TheiaException( "Both 'image' and 'placeholder' are null" )

/** [TheiaException] describing that no permissions have been provided for use cache */
class MissingCacheStoragePermissionsException internal constructor() : TheiaException(
    "Impossible to use cache due to missing storage permissions. Please provide 'READ_EXTERNAL_STORAGE' and " +
            "'WRITE_EXTERNAL_STORAGE' permissions"
)

/**
 * [TheiaException] describing that the Request is useless since none of [TheiaBuilder.target] and
 * [TheiaBuilder.completionCallback] is supplied
 */
class PointlessRequestException internal constructor() :
        TheiaException( "The request is useless since both 'target' and 'completionCallback' are null" )

/** [TheiaException] describing that both [TheiaBuilder.target] and [TheiaBuilder.dimensions] are null */
class UndefinedDimensionsException internal constructor() :
    TheiaException( "If no 'target' is declared, 'dimensions' should be defined" )

/** @return a [TheiaException] generated from the given [Throwable] */
internal fun Throwable.toTheiaException() = when( this ) {
    is CancellationException -> CancelledRequestException()
    else -> TheiaException( cause = this )
}
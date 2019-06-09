package studio.forface.theia

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import studio.forface.theia.utils.*

/** A class that holds an [Image] that is of type [Bitmap] or [Drawable] */
sealed class ResponseWrapper<Image : Any>( val image: Image )

/** [ResponseWrapper] of type [Bitmap] */
class BitmapResponse( image: Bitmap ) : ResponseWrapper<Bitmap>( image )

/** [ResponseWrapper] of type [Drawable] */
class DrawableResponse( image: Drawable) : ResponseWrapper<Drawable>( image ) {

    /** @return [BitmapResponse] transforming [image] in [Bitmap] */
    fun toBitmapResponse() = BitmapResponse( image.toHighResBitmap() )
}

/** Typealias of [ResponseWrapper] of any type */
typealias TheiaResponse = ResponseWrapper<*>

/** @return [BitmapResponse] wrapping the receiver [Bitmap] */
internal fun Bitmap.toResponse() = BitmapResponse( this )

/** @return [DrawableResponse] wrapping the receiver [Drawable] */
internal fun Drawable.toResponse() = DrawableResponse( this )

/**
 * @return [TheiaResponse] wrapping the receiver [Any]
 * @throws IllegalArgumentException if receiver is not [Bitmap] or [Drawable]
 */
@PublishedApi
internal fun Any.toTheiaResponse() : TheiaResponse {
    return when ( this ) {
        is Bitmap -> this.toResponse()
        is Drawable -> this.toResponse()
        else -> throw IllegalArgumentException()
    }
}

/** Set the given [TheiaResponse] as image of the receiver [ImageView] */
internal fun ImageView.setImage( response: TheiaResponse, animationLoop: AnimationLoop ) {
    withDissolve {
        when (response) {
            is BitmapResponse -> setImageBitmap( response.image )
            is DrawableResponse -> {
                val drawable = response.image
                setImageDrawable( response.image )
                when (animationLoop) {
                    is AnimationLoop.NoLoop -> { /* noop */ }
                    is AnimationLoop.Once -> drawable.animate()
                    is AnimationLoop.Forever -> drawable.animateForever()
                    is AnimationLoop.Every -> drawable.animateEvery( this, animationLoop.duration )
                    is AnimationLoop.OnClick -> setOnClickListener { drawable.animate() }
                }
            }
        }.exhaustive
    }
}
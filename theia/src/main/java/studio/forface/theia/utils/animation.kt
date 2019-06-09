package studio.forface.theia.utils

import android.animation.Animator
import android.graphics.drawable.Animatable
import android.graphics.drawable.Animatable2
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.view.postDelayed
import studio.forface.theia.Duration

/** Start ONCE the animation of the receiver [Drawable] if any */
internal fun Drawable.animate() {
    if ( this is Animatable ) start()
    if ( Android.MARSHMALLOW && this is Animatable2 ) start()
}

/** Loop FOREVER the animation of the receiver [Drawable] if any */
internal fun Drawable.animateForever() {
    onAnimationEnd( ::animate )
    animate()
}

/** Loop FOREVER the animation of the receiver [Drawable], every [duration], if any */
internal fun Drawable.animateEvery( imageView: ImageView, duration: Duration ) {
    onAnimationEnd { imageView.postDelayed( duration.timeMs, ::animate ) }
    animate()
}

/** Executes [block] after the animation ended */
private inline fun Drawable.onAnimationEnd( crossinline block: () -> Unit ) {
    if ( Android.MARSHMALLOW && this is Animatable2 ) {
        registerAnimationCallback( object : Animatable2.AnimationCallback() {
            override fun onAnimationEnd( drawable: Drawable ) {
                super.onAnimationEnd( drawable )
                block()
            }
        } )
    }
}

/** Execute the lambda [block] within a dissolve animation on the [ImageView] */
internal inline fun ImageView.withDissolve( crossinline block: ImageView.() -> Unit ) {
    dissolve().setListener( object : Animator.AnimatorListener {
        /**
         *
         * Notifies the end of the animation. This callback is not invoked
         * for animations with repeat count set to INFINITE.
         *
         * @param animation The animation which reached its end.
         */
        override fun onAnimationEnd( animation: Animator ) {
            animation.removeListener( this )
            block()
            condense().start()
        }

        /**
         *
         * Notifies the repetition of the animation.
         *
         * @param animation The animation which was repeated.
         */
        override fun onAnimationRepeat( animation: Animator ) { /* noop */ }

        /**
         *
         * Notifies the cancellation of the animation. This callback is not invoked
         * for animations with repeat count set to INFINITE.
         *
         * @param animation The animation which was canceled.
         */
        override fun onAnimationCancel( animation: Animator ) { /* noop */ }

        /**
         *
         * Notifies the start of the animation.
         *
         * @param animation The started animation.
         */
        override fun onAnimationStart( animation: Animator ) { /* noop */ }
    } ).start()
}

/** The duration of the dissolve animation */
private const val ANIMATION_DURATION = 181L

/** Dissolve the [ImageView] with an animation */
@PublishedApi internal fun ImageView.dissolve() = animate().setDuration( ANIMATION_DURATION ).alpha( 0.2f )

/** Condense the [ImageView] with an animation */
@PublishedApi internal fun ImageView.condense() = animate().setDuration( ANIMATION_DURATION ).alpha( 1f )
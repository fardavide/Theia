package studio.forface.theia.utils

import android.graphics.drawable.Animatable
import android.graphics.drawable.Animatable2
import android.graphics.drawable.Drawable

/** Start ONCE the animation of the receiver [Drawable] if any */
internal fun Drawable.animate() {
    if ( this is Animatable ) start()
    if ( Android.MARSHMALLOW && this is Animatable2 ) start()
}

/** Loop FOREVER the animation of the receiver [Drawable] if any */
internal fun Drawable.animateForever() {
    if ( this is Animatable ) start()
    if ( Android.MARSHMALLOW && this is Animatable2 ) {
        this.registerAnimationCallback( object : Animatable2.AnimationCallback() {
            override fun onAnimationEnd( drawable: Drawable ) {
                super.onAnimationEnd( drawable )
                start()
            }
        } )
        start()
    }
}
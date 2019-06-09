package studio.forface.theia.utils

import android.graphics.drawable.Animatable
import android.graphics.drawable.Animatable2
import android.graphics.drawable.Drawable

/** Start the animation of the receiver [Drawable] if any */
internal fun Drawable.animate() {
    if ( this is Animatable ) start()
    if ( Android.MARSHMALLOW && this is Animatable2 ) start()
}
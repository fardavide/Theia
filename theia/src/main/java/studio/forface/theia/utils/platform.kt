package studio.forface.theia.utils

import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Execute a [block] when [LifecycleOwner] pass the [ON_DESTROY] event
 *
 * @param removeObserver if `true` call [Lifecycle.removeObserver]
 * Default is `false`
 */
internal inline fun LifecycleOwner.doOnDestroy(
    removeObserver: Boolean = false,
    crossinline block: LifecycleOwner.(LifecycleObserver) -> Unit
) {
    val observer = object : DefaultLifecycleObserver {
        override fun onDestroy( owner: LifecycleOwner ) {
            if ( removeObserver ) lifecycle.removeObserver(this )
            owner.block( this )
        }
    }
    lifecycle.addObserver( observer )
}

/**
 * Execute a [block] when [View] is detached.
 *
 * @param removeListener if `true` call [View.removeOnAttachStateChangeListener]
 * Default is `false`
 */
internal inline fun View.doOnDetach(
    removeListener: Boolean = false,
    crossinline block: View.(View.OnAttachStateChangeListener) -> Unit
) {
    val listener = object : View.OnAttachStateChangeListener {
        override fun onViewDetachedFromWindow( view: View ) {
            if ( removeListener ) view.removeOnAttachStateChangeListener(this )
            view.block( this )
        }
        override fun onViewAttachedToWindow( view: View ) { /* Ignore */ }
    }
    addOnAttachStateChangeListener( listener )
}
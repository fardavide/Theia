@file:Suppress("unused")

package studio.forface.theia.utils

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES

/**
 * @author Davide Giuseppe Farella.
 * An object for Android generic utilities.
 */
internal object Android {

    /** @return [Boolean] whether the current SDK if equals of greater that Android Kitkat */
    val KITKAT get() = SDK_INT >= VERSION_CODES.KITKAT

    /** @return [Boolean] whether the current SDK if equals of greater that Android Marshmallow */
    val MARSHMALLOW get() = SDK_INT >= VERSION_CODES.M

    /** @return [Boolean] whether the current SDK if equals of greater that Android Oreo */
    val OREO get() =  SDK_INT >= VERSION_CODES.O

    /** @return [Boolean] whether the current SDK if equals of greater that Android Pie */
    val PIE get() = SDK_INT >= VERSION_CODES.P
}
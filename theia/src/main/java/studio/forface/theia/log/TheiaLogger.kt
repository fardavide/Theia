package studio.forface.theia.log

import android.util.Log
import studio.forface.theia.THEIA_NAME
import studio.forface.theia.TheiaConfig
import studio.forface.theia.TheiaException
import studio.forface.theia.actualMessage

/**
 * An interface for print log messages
 *
 * @author Davide Giuseppe Farella
 */
interface TheiaLogger {

    /** @return [TheiaConfig.loggingEnabled] */
    val logEnabled get() = TheiaConfig.loggingEnabled

    /**
     * Print an error message from [TheiaException]
     * When overriding this function, remember to handle [logEnabled] since we want to deliver some critical errors to
     * the user, even if logging is disabled.
     */
    fun error( ex: TheiaException )

    /**
     * Print an info message from [TheiaException]
     * When overriding this function, remember to handle [logEnabled] since we want to deliver some critical errors to
     * the user, even if logging is disabled.
     */
    fun info( ex: TheiaException )
}

/** Default implementation of [TheiaLogger] */
object DefaultTheiaLogger : TheiaLogger {

    /** Print an error message from [TheiaException] */
    override fun error( ex: TheiaException ) {
        if ( logEnabled ) ex.printStackTrace()
        else Log.d( THEIA_NAME, ex.actualMessage )
    }

    /** Print an info message from [TheiaException] */
    override fun info( ex: TheiaException ) {
        if ( logEnabled ) Log.d( THEIA_NAME, ex.actualMessage )
    }
}
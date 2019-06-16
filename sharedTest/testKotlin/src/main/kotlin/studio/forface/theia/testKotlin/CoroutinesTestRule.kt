@file:Suppress("EXPERIMENTAL_API_USAGE") // Coroutines Test API

package studio.forface.theia.testKotlin

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * A JUnit Test Rule that set a Main Dispatcher
 * @author Davide Farella
 */
class CoroutinesTestRule : TestWatcher() {

    private val mainThreadSurrogate = newSingleThreadContext( "UI thread" )

    override fun starting( description: Description? ) {
        super.starting( description )
        Dispatchers.setMain( mainThreadSurrogate )
    }

    override fun finished( description: Description? ) {
        super.finished( description )
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }
}

package studio.forface.theia.dsl

/**
 * An [Exception] that will be thrown when GET will be called on a DSL `var` that doesn't have a
 * proper way for get a value.
 *
 * @author Davide Giuseppe Farella
 */
class DslGetException internal constructor(): Exception( "Can not call getter on a DSL param" )

/** Throw a [DslGetException] */
internal fun <T> throwDslGetException(): T {
    throw DslGetException()
}
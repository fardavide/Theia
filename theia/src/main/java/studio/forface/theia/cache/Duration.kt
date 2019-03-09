package studio.forface.theia.cache

/**
 * An inline class representing the time with math operators
 *
 * @author Davide Giuseppe Farella
 */
@Suppress("EXPERIMENTAL_FEATURE_WARNING")
inline class Duration(internal val timeMs: Long ) {

    internal operator fun plus( other: Duration ) = Duration(timeMs + other.timeMs )
    internal operator fun minus( other: Duration ) = Duration(timeMs - other.timeMs )
    internal operator fun times( multiplier: Int ) = Duration(timeMs * multiplier )
    internal operator fun div( divider: Int ) = Duration(timeMs / divider )
}

internal operator fun Long.minus( duration: Duration ) = this - duration.timeMs
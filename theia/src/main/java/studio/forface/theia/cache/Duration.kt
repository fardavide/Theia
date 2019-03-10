@file:Suppress("unused")

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

/** @return a [Duration] of the given value in minutes */
val Int.mins get() = Duration(this.toLong() * 1000 /* ms */ * 60 /* seconds */ )

/** @return a [Duration] of the given value in hours */
val Int.hours get() = 60.mins * this

/** @return a [Duration] of the given value in days */
val Int.days get() = 24.hours * this

/** @return a [Duration] of the given value in weeks */
val Int.weeks get() = 7.days * this

/** @return a [Duration] of the given value in months */
val Int.months get() = 30.days * this
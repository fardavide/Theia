@file:Suppress("FunctionName")

package studio.forface.theia.cache

import studio.forface.theia.Duration
import studio.forface.theia.TheiaConfig
import studio.forface.theia.mins
import studio.forface.theia.minus
import java.io.File
import java.lang.System.currentTimeMillis
import studio.forface.theia.TheiaConfig.defaultCacheDirectory as cacheDir

/**
 * Delete all the [File]s with extension [CACHE_EXT] with [File.lastModified] older than [olderThan] from
 * [TheiaConfig.defaultCacheDirectory]
 *
 * @param olderThan A duration for query files to delete.
 * Default is 0 minutes ( `0.mins` ), soo all the caches will be removed
 */
internal fun _cleanCache( olderThan: Duration = 0.mins ) {
    if ( ! cacheDir.exists() ) return
    cacheDir.listFiles { _, name -> name.endsWith( CACHE_EXT ) }
        .filter { it.lastModified() < currentTimeMillis() - olderThan }
        .forEach { it.delete() }
}
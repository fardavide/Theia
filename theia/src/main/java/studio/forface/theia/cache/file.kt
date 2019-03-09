package studio.forface.theia.cache

import java.io.File

/** @return a child [File] of receiver [File] with the given [fileName] if [File.exists], else `null` */
internal operator fun File.get( fileName: String ) = with( File(this, fileName ) ) {
    if ( exists() ) this else null
}

/**
 * @return a child [File] of receiver [File] with the given [fileName] if [File.exists], and [File.lastModified]
 * is greater that [lastModifiedAfter]; else delete it.
 */
internal operator fun File.get( fileName: String, lastModifiedAfter: Long ) = this[fileName]?.let {
    if ( it.lastModified() > lastModifiedAfter ) it
    else {
        it.delete()
        null
    }
}

/** @return a child [File] of receiver [File] with the given [fileName] */
internal operator fun File.plus( fileName: String ) = File(this, fileName )

/** Write the [ByteArray] in the [File] with [fileName] and set [File.setLastModified] as [System.currentTimeMillis] */
internal operator fun File.set( fileName: String, byteArray: ByteArray ) {
    with ( this + fileName ) {
        writeBytes( byteArray )
        setLastModified( System.currentTimeMillis() )
    }
}
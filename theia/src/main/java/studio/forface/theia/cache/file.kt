package studio.forface.theia.cache

import java.io.File

/** @return a child [File] of receiver [File] with the given [fileName] if [File.exists], else `null` */
internal operator fun File.get( fileName: String ): File? {
    return with ( this + fileName ) {
        if ( exists() ) this else null
    }
}

/**
 * @return a child [File] of receiver [File] with the given [fileName] if [File.exists], and [File.lastModified]
 * is greater that [lastModifiedAfter]; else delete it.
 */
internal operator fun File.get( fileName: String, lastModifiedAfter: Long ): File? {
    val file = this[fileName] ?: return null

    return if ( file.lastModified() > lastModifiedAfter ) file
    else null.also { file.delete() }
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
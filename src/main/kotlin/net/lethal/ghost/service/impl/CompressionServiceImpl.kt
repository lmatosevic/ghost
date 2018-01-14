package net.lethal.ghost.service.impl

import net.lethal.ghost.service.CompressionService
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets.UTF_8
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

class CompressionServiceImpl : CompressionService {

    override fun compress(data: String): ByteArray {
        val bos = ByteArrayOutputStream()
        GZIPOutputStream(bos).bufferedWriter(UTF_8).use { it.write(data) }
        return bos.toByteArray()
    }

    override fun decompress(data: ByteArray): String {
        return GZIPInputStream(data.inputStream()).bufferedReader(UTF_8).use { it.readText() }
    }
}
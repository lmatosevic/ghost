package net.lethal.ghost.service

interface CompressionService {

    fun compress(data: String): ByteArray

    fun decompress(data: ByteArray): String
}
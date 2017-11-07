package net.lethal.ghost.service

interface LoggerService {

    fun info(message: String)

    fun error(message: String)

    fun warning(message: String)
}
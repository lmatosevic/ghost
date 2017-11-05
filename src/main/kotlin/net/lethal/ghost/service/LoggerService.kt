package net.lethal.ghost.service

interface LoggerService {

    fun info(text: String)

    fun error(text: String)
}
package net.lethal.ghost.service.impl

import net.lethal.ghost.service.LoggerService

class LoggerServiceImpl : LoggerService {

    override fun info(message: String) = println("INFO: $message")

    override fun error(message: String) = System.err.println("ERROR: $message")
}
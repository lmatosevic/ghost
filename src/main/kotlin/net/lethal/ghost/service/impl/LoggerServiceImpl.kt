package net.lethal.ghost.service.impl

import net.lethal.ghost.service.LoggerService

class LoggerServiceImpl : LoggerService {

    override fun info(text: String) = println(text)

    override fun error(text: String) = System.err.println(text)
}
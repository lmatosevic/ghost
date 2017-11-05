package net.lethal.ghost.app

import com.google.inject.AbstractModule
import net.lethal.ghost.service.LoggerService
import net.lethal.ghost.service.impl.LoggerServiceImpl

class GhostModule : AbstractModule() {

    override fun configure() {
        bind(LoggerService::class.java).to(LoggerServiceImpl::class.java)
    }
}
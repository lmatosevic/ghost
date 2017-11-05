package net.lethal.ghost.app

import com.google.inject.AbstractModule
import net.lethal.ghost.service.LoggerService
import net.lethal.ghost.service.impl.KeyboardListenerService
import net.lethal.ghost.service.impl.LoggerServiceImpl
import net.lethal.ghost.service.impl.MouseListenerService

class GhostModule : AbstractModule() {

    override fun configure() {
        bind(LoggerService::class.java).to(LoggerServiceImpl::class.java)
        bind(KeyboardListenerService::class.java).toInstance(KeyboardListenerService())
        bind(MouseListenerService::class.java).toInstance(MouseListenerService())
    }
}
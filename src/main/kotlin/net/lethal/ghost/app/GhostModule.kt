package net.lethal.ghost.app

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import net.lethal.ghost.service.LoggerService
import net.lethal.ghost.service.impl.KeyboardListenerService
import net.lethal.ghost.service.impl.LoggerServiceImpl
import net.lethal.ghost.service.impl.MouseListenerService

class GhostModule : AbstractModule() {

    override fun configure() {
        bind(LoggerService::class.java).to(LoggerServiceImpl::class.java)
    }

    @Provides
    @Singleton
    fun provideKeyboardListenerService(): KeyboardListenerService {
        return KeyboardListenerService()
    }

    @Provides
    @Singleton
    fun provideMouseListenerService(): MouseListenerService {
        return MouseListenerService()
    }
}
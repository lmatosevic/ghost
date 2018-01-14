package net.lethal.ghost.app

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import net.lethal.ghost.service.CompressionService
import net.lethal.ghost.service.LoggerService
import net.lethal.ghost.service.ScenarioFileService
import net.lethal.ghost.service.ScenarioHolderService
import net.lethal.ghost.service.impl.*

class GhostModule : AbstractModule() {

    override fun configure() {
        bind(LoggerService::class.java).to(LoggerServiceImpl::class.java)
        bind(ScenarioFileService::class.java).to(ScenarioFileServiceImpl::class.java)
        bind(CompressionService::class.java).to(CompressionServiceImpl::class.java)
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

    @Provides
    @Singleton
    fun provideScenarioHolderService(): ScenarioHolderService {
        return ScenarioHolderServiceImpl()
    }
}
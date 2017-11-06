package net.lethal.ghost.service.impl

import net.lethal.ghost.service.LifecycleService
import tornadofx.*

abstract class ListenerService : Component(), LifecycleService {
    var started = false
    var paused = false

    override fun start() {
        registerListeners()
        started = true
        paused = false
    }

    override fun stop() {
        removeListeners()
        started = false
    }

    override fun pause() {
        paused = true
    }

    abstract fun registerListeners()

    abstract fun removeListeners()
}
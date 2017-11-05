package net.lethal.ghost.service.impl

import net.lethal.ghost.service.LifecycleService
import tornadofx.*

open class ListenerService : Component(), LifecycleService {
    var started = false
    var paused = false

    override fun start() {
        started = true
        paused = false
    }

    override fun stop() {
        started = false
    }

    override fun pause() {
        paused = true
    }
}
package net.lethal.ghost.service.impl

import javafx.application.Platform
import java.util.*
import java.util.concurrent.AbstractExecutorService
import java.util.concurrent.TimeUnit

class NativeDispatchService : AbstractExecutorService() {
    private var running = false

    init {
        running = true
    }

    override fun shutdown() {
        running = false
    }

    override fun shutdownNow(): MutableList<Runnable> {
        running = false
        return ArrayList(0)
    }

    override fun isTerminated(): Boolean {
        return !running
    }

    override fun isShutdown(): Boolean {
        return !running
    }

    override fun awaitTermination(timeout: Long, unit: TimeUnit?): Boolean {
        return true
    }

    override fun execute(command: Runnable?) {
        Platform.runLater(command)
    }
}
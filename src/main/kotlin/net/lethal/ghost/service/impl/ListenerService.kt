package net.lethal.ghost.service.impl

import net.lethal.ghost.event.Event
import net.lethal.ghost.event.EventProvider
import net.lethal.ghost.event.EventSubscriber
import net.lethal.ghost.service.LifecycleService
import java.util.concurrent.atomic.AtomicInteger

abstract class ListenerService : LifecycleService, EventProvider {
    var started = false
    var paused = false

    override var subscribers: MutableList<EventSubscriber> = arrayListOf()

    override fun start() {
        if (!started && !paused) {
            registerSelf()
            order.set(1)
        }
        started = true
        paused = false
    }

    override fun stop() {
        removeSelf()
        started = false
        paused = false
    }

    override fun pause() {
        paused = true
    }

    override fun addSubscriber(subscriber: EventSubscriber) {
        subscribers.add(subscriber)
    }

    override fun removeSubscriber(subscriber: EventSubscriber) {
        subscribers.remove(subscriber)
    }

    override fun notifySubscribers(event: Event) {
        subscribers.forEach {
            notifySubscriber(it, event)
            it.onEvent(event)
        }
    }

    abstract fun notifySubscriber(subscriber: EventSubscriber, event: Event)

    abstract fun registerSelf()

    abstract fun removeSelf()

    companion object {
        @JvmStatic
        var order: AtomicInteger = AtomicInteger(1)
    }
}
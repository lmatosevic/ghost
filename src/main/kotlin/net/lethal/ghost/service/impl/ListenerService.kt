package net.lethal.ghost.service.impl


import net.lethal.ghost.event.Event
import net.lethal.ghost.event.EventProvider
import net.lethal.ghost.event.EventSubscriber
import net.lethal.ghost.event.action.ActionType
import net.lethal.ghost.event.action.other.WaitAction
import net.lethal.ghost.event.type.WaitEvent
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
            lastEventTime = System.currentTimeMillis()
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
        if (event.action.type != ActionType.WAIT) {
            val waitTime: Int = (System.currentTimeMillis() - lastEventTime).toInt()
            if (waitTime >= 500) {
                notifySubscribers(WaitEvent(order.getAndIncrement(), waitTime.toLong(), WaitAction(waitTime)))
            }
        }
        subscribers.forEach {
            if (event.action.type != ActionType.WAIT) {
                notifySubscriber(it, event)
            }
            it.onEvent(event)
        }
        lastEventTime = System.currentTimeMillis()
    }

    abstract fun notifySubscriber(subscriber: EventSubscriber, event: Event)

    abstract fun registerSelf()

    abstract fun removeSelf()

    companion object {
        @JvmStatic
        var order: AtomicInteger = AtomicInteger(1)

        @JvmStatic
        var lastEventTime: Long = System.currentTimeMillis()
    }
}
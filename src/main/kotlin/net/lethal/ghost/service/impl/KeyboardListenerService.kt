package net.lethal.ghost.service.impl

import net.lethal.ghost.app.Context
import net.lethal.ghost.event.Event
import net.lethal.ghost.event.EventSubscriber
import net.lethal.ghost.event.action.keyboard.KeyPressedAction
import net.lethal.ghost.event.action.keyboard.KeyReleasedAction
import net.lethal.ghost.event.type.KeyboardEvent
import net.lethal.ghost.service.LoggerService
import org.jnativehook.GlobalScreen
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener

class KeyboardListenerService : ListenerService(), NativeKeyListener {
    private val logger: LoggerService by Context.di()

    override fun registerSelf() {
        GlobalScreen.addNativeKeyListener(this)
    }

    override fun removeSelf() {
        GlobalScreen.removeNativeKeyListener(this)
    }

    override fun notifySubscriber(subscriber: EventSubscriber, event: Event) {
        subscriber.onKeyboardEvent(event as KeyboardEvent)
    }

    override fun nativeKeyPressed(event: NativeKeyEvent?) {
        val rawCode = event?.rawCode
        if (paused || keyMapPressedTimestamp.containsKey(rawCode)) return
        keyMapPressedTimestamp.put(rawCode!!, System.currentTimeMillis())
        notifySubscribers(KeyboardEvent(order.getAndIncrement(), 50, KeyPressedAction(rawCode)))
    }

    override fun nativeKeyReleased(event: NativeKeyEvent?) {
        val rawCode = event?.rawCode
        if (paused || !keyMapPressedTimestamp.containsKey(rawCode)) return
        keyMapPressedTimestamp.remove(rawCode!!)
        notifySubscribers(KeyboardEvent(order.getAndIncrement(), 50, KeyReleasedAction(rawCode)))
    }

    override fun nativeKeyTyped(event: NativeKeyEvent?) {
        // ignored
    }

    companion object {
        val keyMapPressedTimestamp: MutableMap<Int, Long> = hashMapOf()
    }
}
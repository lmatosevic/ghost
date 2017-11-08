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
import java.util.*

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
        if (paused) return
        notifySubscribers(KeyboardEvent(Date(), Date(), KeyPressedAction(event?.rawCode)))
    }

    override fun nativeKeyReleased(event: NativeKeyEvent?) {
        if (paused) return
        notifySubscribers(KeyboardEvent(Date(), Date(), KeyReleasedAction(event?.rawCode)))
    }

    override fun nativeKeyTyped(event: NativeKeyEvent?) {
        // ignored
    }
}
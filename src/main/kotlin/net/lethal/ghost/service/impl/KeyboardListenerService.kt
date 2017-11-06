package net.lethal.ghost.service.impl

import net.lethal.ghost.service.LoggerService
import org.jnativehook.GlobalScreen
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener

class KeyboardListenerService : ListenerService(), NativeKeyListener {
    private val logger: LoggerService by di()

    override fun registerListeners() {
        GlobalScreen.addNativeKeyListener(this)
    }

    override fun removeListeners() {
        GlobalScreen.removeNativeKeyListener(this)
    }

    override fun nativeKeyTyped(p0: NativeKeyEvent?) {
        if (!paused) logger.info("Key typed")
    }

    override fun nativeKeyPressed(p0: NativeKeyEvent?) {
        if (!paused) logger.info("Key pressed")
    }

    override fun nativeKeyReleased(p0: NativeKeyEvent?) {
        if (!paused) logger.info("Key released")
    }
}
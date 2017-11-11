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
import java.awt.event.KeyEvent

class KeyboardListenerService : ListenerService(), NativeKeyListener {
    private val logger: LoggerService by Context.di()

    companion object {
        val keyMapPressedTimestamp: MutableMap<Int, Long> = hashMapOf()
    }

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
        val finalCode = toFinalKeyCode(event!!)
        if (paused || keyMapPressedTimestamp.containsKey(finalCode)) return
        keyMapPressedTimestamp.put(finalCode, System.currentTimeMillis())
        logger.info("press keyCode=${event.keyCode} rawCode=${event.rawCode} finalCode=$finalCode")
        notifySubscribers(KeyboardEvent(order.getAndIncrement(), 50, KeyPressedAction(finalCode)))
    }

    override fun nativeKeyReleased(event: NativeKeyEvent?) {
        val finalCode = toFinalKeyCode(event!!)
        if (paused || !keyMapPressedTimestamp.containsKey(finalCode)) return
        keyMapPressedTimestamp.remove(finalCode)
        logger.info("released keyCode=${event.keyCode} rawCode=${event.rawCode} finalCode=$finalCode")
        notifySubscribers(KeyboardEvent(order.getAndIncrement(), 50, KeyReleasedAction(finalCode)))
    }

    override fun nativeKeyTyped(event: NativeKeyEvent?) {
        // ignored
    }

    private fun toFinalKeyCode(event: NativeKeyEvent): Int {
        return when (event.keyCode) {
            NativeKeyEvent.VC_ENTER -> KeyEvent.VK_ENTER
            NativeKeyEvent.VC_SHIFT -> KeyEvent.VK_SHIFT
            NativeKeyEvent.VC_BACKSPACE -> KeyEvent.VK_BACK_SPACE
            NativeKeyEvent.VC_CONTROL -> KeyEvent.VK_CONTROL
            NativeKeyEvent.VC_ALT -> KeyEvent.VK_ALT
            NativeKeyEvent.VC_TAB -> KeyEvent.VK_TAB
            NativeKeyEvent.VC_CAPS_LOCK -> KeyEvent.VK_CAPS_LOCK
            NativeKeyEvent.VC_ESCAPE -> KeyEvent.VK_ESCAPE
            NativeKeyEvent.VC_PRINTSCREEN -> if (event.rawCode == 44) KeyEvent.VK_PRINTSCREEN else event.rawCode
            NativeKeyEvent.VC_SCROLL_LOCK -> KeyEvent.VK_SCROLL_LOCK
            NativeKeyEvent.VC_PAUSE -> KeyEvent.VK_PAUSE
            NativeKeyEvent.VC_INSERT -> KeyEvent.VK_INSERT
            NativeKeyEvent.VC_HOME -> KeyEvent.VK_HOME
            NativeKeyEvent.VC_PAGE_UP -> KeyEvent.VK_PAGE_UP
            NativeKeyEvent.VC_DELETE -> KeyEvent.VK_DELETE
            NativeKeyEvent.VC_END -> KeyEvent.VK_END
            NativeKeyEvent.VC_PAGE_DOWN -> KeyEvent.VK_PAGE_DOWN
            NativeKeyEvent.VC_NUM_LOCK -> KeyEvent.VK_NUM_LOCK
            NativeKeyEvent.VC_F1 -> KeyEvent.VK_F1
            NativeKeyEvent.VC_F2 -> KeyEvent.VK_F2
            NativeKeyEvent.VC_F3 -> KeyEvent.VK_F3
            NativeKeyEvent.VC_F4 -> KeyEvent.VK_F4
            NativeKeyEvent.VC_F5 -> KeyEvent.VK_F5
            NativeKeyEvent.VC_F6 -> KeyEvent.VK_F6
            NativeKeyEvent.VC_F7 -> KeyEvent.VK_F7
            NativeKeyEvent.VC_F8 -> KeyEvent.VK_F8
            NativeKeyEvent.VC_F9 -> KeyEvent.VK_F9
            NativeKeyEvent.VC_F10 -> KeyEvent.VK_F10
            NativeKeyEvent.VC_F11 -> KeyEvent.VK_F11
            NativeKeyEvent.VC_F12 -> KeyEvent.VK_F12
            NativeKeyEvent.VC_OPEN_BRACKET -> KeyEvent.VK_S
            NativeKeyEvent.VC_CLOSE_BRACKET -> KeyEvent.VK_D
            NativeKeyEvent.VC_SEMICOLON -> KeyEvent.VK_C
            NativeKeyEvent.VC_BACK_SLASH -> KeyEvent.VK_Z
            NativeKeyEvent.VC_QUOTE -> KeyEvent.VK_C
            else -> event.rawCode
        }
    }
}
package net.lethal.ghost.service.impl

import net.lethal.ghost.app.Context
import net.lethal.ghost.event.Event
import net.lethal.ghost.event.EventSubscriber
import net.lethal.ghost.event.action.mouse.MouseMovedAction
import net.lethal.ghost.event.action.mouse.MousePressedAction
import net.lethal.ghost.event.action.mouse.MouseReleasedAction
import net.lethal.ghost.event.action.mouse.MouseWheelAction
import net.lethal.ghost.event.type.MouseEvent
import net.lethal.ghost.service.LoggerService
import org.jnativehook.GlobalScreen
import org.jnativehook.mouse.*
import java.awt.event.InputEvent

class MouseListenerService : ListenerService(), NativeMouseInputListener, NativeMouseMotionListener, NativeMouseWheelListener {
    private val logger: LoggerService by Context.di()

    override fun registerSelf() {
        GlobalScreen.addNativeMouseListener(this)
        GlobalScreen.addNativeMouseMotionListener(this)
        GlobalScreen.addNativeMouseWheelListener(this)
    }

    override fun removeSelf() {
        GlobalScreen.removeNativeMouseListener(this)
        GlobalScreen.removeNativeMouseMotionListener(this)
        GlobalScreen.removeNativeMouseWheelListener(this)
    }

    override fun notifySubscriber(subscriber: EventSubscriber, event: Event) {
        subscriber.onMouseEvent(event as MouseEvent)
    }

    override fun nativeMouseMoved(event: NativeMouseEvent?) {
        if (paused) return
        notifySubscribers(MouseEvent(order.getAndIncrement(), 100, MouseMovedAction(event?.x!!, event.y)))
    }

    override fun nativeMousePressed(event: NativeMouseEvent?) {
        if (paused) return
        notifySubscribers(MouseEvent(order.getAndIncrement(), 100, MousePressedAction(toRawMouseButton(event!!.button))))
    }

    override fun nativeMouseReleased(event: NativeMouseEvent?) {
        if (paused) return
        notifySubscribers(MouseEvent(order.getAndIncrement(), 100, MouseReleasedAction(toRawMouseButton(event!!.button))))
    }

    override fun nativeMouseWheelMoved(event: NativeMouseWheelEvent?) {
        if (paused) return
        notifySubscribers(MouseEvent(order.getAndIncrement(), 100, MouseWheelAction(event!!.wheelRotation)))
    }

    override fun nativeMouseDragged(event: NativeMouseEvent?) {
        // Unused
    }

    override fun nativeMouseClicked(event: NativeMouseEvent?) {
        // ignored
    }

    private fun toRawMouseButton(button: Int): Int {
        return when (button) {
            1 -> InputEvent.BUTTON1_MASK
            2 -> InputEvent.BUTTON3_MASK
            3 -> InputEvent.BUTTON2_MASK
            else -> 0
        }
    }
}

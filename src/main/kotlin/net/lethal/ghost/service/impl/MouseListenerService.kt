package net.lethal.ghost.service.impl

import net.lethal.ghost.event.Event
import net.lethal.ghost.event.EventSubscriber
import net.lethal.ghost.event.action.mouse.*
import net.lethal.ghost.event.type.MouseEvent
import org.jnativehook.GlobalScreen
import org.jnativehook.mouse.*
import java.util.*

class MouseListenerService : ListenerService(), NativeMouseInputListener, NativeMouseMotionListener, NativeMouseWheelListener {

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
        notifySubscribers(MouseEvent(Date(), Date(), MouseMovedAction()))
    }

    override fun nativeMouseDragged(event: NativeMouseEvent?) {
        if (paused) return
        notifySubscribers(MouseEvent(Date(), Date(), MouseDraggedAction()))
    }

    override fun nativeMousePressed(event: NativeMouseEvent?) {
        if (paused) return
        notifySubscribers(MouseEvent(Date(), Date(), MousePressedAction()))
    }

    override fun nativeMouseReleased(event: NativeMouseEvent?) {
        if (paused) return
        notifySubscribers(MouseEvent(Date(), Date(), MouseReleasedAction()))
    }

    override fun nativeMouseWheelMoved(event: NativeMouseWheelEvent?) {
        if (paused) return
        notifySubscribers(MouseEvent(Date(), Date(), MouseWheelAction()))
    }

    override fun nativeMouseClicked(event: NativeMouseEvent?) {
        // ignored
    }
}

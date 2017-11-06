package net.lethal.ghost.service.impl

import net.lethal.ghost.service.LoggerService
import org.jnativehook.GlobalScreen
import org.jnativehook.mouse.*

class MouseListenerService : ListenerService(), NativeMouseInputListener, NativeMouseMotionListener, NativeMouseWheelListener {
    private val logger: LoggerService by di()

    override fun registerListeners() {
        GlobalScreen.addNativeMouseListener(this)
        GlobalScreen.addNativeMouseMotionListener(this)
        GlobalScreen.addNativeMouseWheelListener(this)
    }

    override fun removeListeners() {
        GlobalScreen.removeNativeMouseListener(this)
        GlobalScreen.removeNativeMouseMotionListener(this)
        GlobalScreen.removeNativeMouseWheelListener(this)
    }

    override fun nativeMouseMoved(p0: NativeMouseEvent?) {
//        logger.info("Mouse moved")
    }

    override fun nativeMouseDragged(p0: NativeMouseEvent?) {
        if (!paused) logger.info("Mouse dragged")
    }

    override fun nativeMousePressed(p0: NativeMouseEvent?) {
        if (!paused) logger.info("Mouse pressed")
    }

    override fun nativeMouseClicked(p0: NativeMouseEvent?) {
        if (!paused) logger.info("Mouse clicked")
    }

    override fun nativeMouseReleased(p0: NativeMouseEvent?) {
        if (!paused) logger.info("Mouse released")
    }

    override fun nativeMouseWheelMoved(p0: NativeMouseWheelEvent?) {
        if (!paused) logger.info("Mouse wheel moved")
    }
}

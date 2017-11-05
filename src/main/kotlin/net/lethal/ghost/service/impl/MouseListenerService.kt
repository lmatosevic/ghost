package net.lethal.ghost.service.impl

import net.lethal.ghost.service.LoggerService
import org.jnativehook.mouse.*

class MouseListenerService : ListenerService(), NativeMouseInputListener, NativeMouseMotionListener, NativeMouseWheelListener {
    private val logger: LoggerService by di()

    override fun nativeMouseMoved(p0: NativeMouseEvent?) {
//        logger.info("Mouse moved")
    }

    override fun nativeMouseDragged(p0: NativeMouseEvent?) {
        if(started && !paused) logger.info("Mouse dragged")
    }

    override fun nativeMousePressed(p0: NativeMouseEvent?) {
        if(started && !paused) logger.info("Mouse pressed")
    }

    override fun nativeMouseClicked(p0: NativeMouseEvent?) {
        if(started && !paused) logger.info("Mouse clicked")
    }

    override fun nativeMouseReleased(p0: NativeMouseEvent?) {
        if(started && !paused) logger.info("Mouse released")
    }

    override fun nativeMouseWheelMoved(p0: NativeMouseWheelEvent?) {
        if(started && !paused) logger.info("Mouse wheel moved")
    }
}

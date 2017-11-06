package net.lethal.ghost.controller

import javafx.scene.layout.VBox
import net.lethal.ghost.app.Context
import net.lethal.ghost.service.LoggerService
import net.lethal.ghost.service.impl.KeyboardListenerService
import net.lethal.ghost.service.impl.MouseListenerService
import tornadofx.*

class MainController : View(Context.windowName) {
    override val root: VBox = loadFXML("/views/MainView.fxml", true)
//    private val nameLabel: Label by fxid()
    private val logger: LoggerService by di()
    private val keyboardListener: KeyboardListenerService by di()
    private val mouseListener: MouseListenerService by di()

    fun play() {
        logger.info("Play pressed")
        if (keyboardListener.paused && mouseListener.paused) {
            keyboardListener.start()
            mouseListener.start()
        }
    }

    fun pause() {
        logger.info("Pause pressed")
        keyboardListener.pause()
        mouseListener.pause()
    }

    fun stop() {
        logger.info("Stop pressed")
        keyboardListener.stop()
        mouseListener.stop()
    }

    fun record() {
        logger.info("Record pressed")
        keyboardListener.start()
        mouseListener.start()
    }

    fun save() {
        logger.info("Save pressed")
    }

    fun open() {
        logger.info("Open pressed")
    }
}

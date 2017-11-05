package net.lethal.ghost.controller

import javafx.scene.layout.VBox
import net.lethal.ghost.app.Context
import net.lethal.ghost.service.LoggerService
import tornadofx.*

class MainController : View(Context.windowName) {
    override val root: VBox = loadFXML("/views/MainView.fxml", true)
//    private val nameLabel: Label by fxid()
    private val logger: LoggerService by di()

    fun play() {
        logger.info("Play pressed")
    }

    fun pause() {
        logger.info("Pause pressed")
    }

    fun stop() {
        logger.info("Stop pressed")
    }

    fun record() {
        logger.info("Record pressed")
    }

    fun save() {
        logger.info("Save pressed")
    }

    fun open() {
        logger.info("Open pressed")
    }
}

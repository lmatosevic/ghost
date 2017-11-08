package net.lethal.ghost.controller

import javafx.scene.control.Button
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import net.lethal.ghost.app.Context
import net.lethal.ghost.component.ButtonInteractionComponent
import net.lethal.ghost.component.ImageInteractionComponent
import net.lethal.ghost.event.Event
import net.lethal.ghost.event.EventSubscriber
import net.lethal.ghost.service.LoggerService
import net.lethal.ghost.service.ScenarioHolderService
import net.lethal.ghost.service.impl.KeyboardListenerService
import net.lethal.ghost.service.impl.MouseListenerService
import tornadofx.*

class MainController : View(Context.windowName), EventSubscriber {
    override val root: VBox = loadFXML("/views/MainView.fxml", true)

    private val keyboardImg: ImageView by fxid()
    private val mouseImg: ImageView by fxid()
    private val playBtn: Button by fxid()
    private val pauseBtn: Button by fxid()
    private val stopBtn: Button by fxid()
    private val recordBtn: Button by fxid()
    private val saveBtn: Button by fxid()
    private val openBtn: Button by fxid()

    private val logger: LoggerService by Context.di()
    private val keyboardListener: KeyboardListenerService by Context.di()
    private val mouseListener: MouseListenerService by Context.di()
    private val scenarioHolder: ScenarioHolderService by Context.di()

    private val imageInteractionComponent: ImageInteractionComponent
    private val buttonInteractionComponent: ButtonInteractionComponent

    init {
        keyboardListener.addSubscriber(this)
        mouseListener.addSubscriber(this)
        imageInteractionComponent = ImageInteractionComponent(keyboardImg, mouseImg)
        buttonInteractionComponent = ButtonInteractionComponent(playBtn, pauseBtn, stopBtn, recordBtn, saveBtn, openBtn)
    }

    override fun onEvent(event: Event) {
        imageInteractionComponent.changeImageColor(event.device)
    }

    fun play() {
        logger.info("Play pressed")
        if (keyboardListener.paused && mouseListener.paused) {
            keyboardListener.start()
            mouseListener.start()
        }
        if (!keyboardListener.started && !mouseListener.started){
            scenarioHolder.execute()
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
        scenarioHolder.save()
    }

    fun open() {
        logger.info("Open pressed")
        scenarioHolder.load()
    }
}

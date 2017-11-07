package net.lethal.ghost.controller

import javafx.scene.effect.Light
import javafx.scene.effect.Lighting
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import net.lethal.ghost.app.Context
import net.lethal.ghost.event.DeviceType
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
    private val lightingEffect: Lighting

    private val logger: LoggerService by di()
    private val keyboardListener: KeyboardListenerService by di()
    private val mouseListener: MouseListenerService by di()
    private val scenarioHolder: ScenarioHolderService by di()

    private var mouseLastEvent: Long = System.currentTimeMillis()
    private var keyboardLastEvent: Long = System.currentTimeMillis()

    init {
        keyboardListener.addSubscriber(this)
        mouseListener.addSubscriber(this)

        val lighting = Lighting(Light.Distant(45.0, 45.0, Color.web("#4191e0")))
        lighting.diffuseConstant = 0.51
        lighting.specularConstant = 1.23
        lighting.specularExponent = 0.93
        lighting.surfaceScale = 3.36
        lightingEffect = lighting
    }

    override fun onEvent(event: Event) {
        changeImageColor(event.device)
    }

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
        scenarioHolder.save()
    }

    fun open() {
        logger.info("Open pressed")
        scenarioHolder.load()
    }

    private fun changeImageColor(deviceType: DeviceType) {
        val imageView: ImageView
        when (deviceType) {
            DeviceType.KEYBOARD -> {
                keyboardLastEvent = System.currentTimeMillis()
                imageView = keyboardImg
            }
            DeviceType.MOUSE -> {
                mouseLastEvent = System.currentTimeMillis()
                imageView = mouseImg
            }
        }

        if (imageView.effect == null) {
            imageView.effect = lightingEffect
        }

        runAsync {
            Thread.sleep(300)
        } ui {
            val lastEvent = if (deviceType == DeviceType.KEYBOARD) keyboardLastEvent else mouseLastEvent
            if (lastEvent < System.currentTimeMillis() - 50) {
                imageView.effect = null
            }
        }
    }
}

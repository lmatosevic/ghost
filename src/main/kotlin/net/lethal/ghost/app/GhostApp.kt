package net.lethal.ghost.app

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.inject.Guice
import javafx.application.Platform
import net.lethal.ghost.config.Configuration
import net.lethal.ghost.controller.MainController
import net.lethal.ghost.service.LoggerService
import net.lethal.ghost.service.impl.KeyboardListenerService
import net.lethal.ghost.service.impl.MouseListenerService
import net.lethal.ghost.service.impl.NativeDispatchService
import net.lethal.ghost.style.Styles
import org.jnativehook.GlobalScreen
import org.jnativehook.NativeHookException
import tornadofx.*
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.reflect.KClass


class GhostApp : App(MainController::class, Styles::class) {
    private val logger: LoggerService
    private val keyboardListener: KeyboardListenerService
    private val mouseListener: MouseListenerService

    init {
        val guice = Guice.createInjector(GhostModule())
        logger = guice.getInstance(LoggerService::class.java)
        keyboardListener = guice.getInstance(KeyboardListenerService::class.java)
        mouseListener = guice.getInstance(MouseListenerService::class.java)

        FX.dicontainer = object : DIContainer {
            override fun <T : Any> getInstance(type: KClass<T>) = guice.getInstance(type.java)
        }

        val logger = Logger.getLogger(GlobalScreen::class.java.`package`.name)
        logger.level = Level.OFF
        logger.useParentHandlers = false

        GlobalScreen.setEventDispatcher(NativeDispatchService())
    }

    override fun onBeforeShow(view: UIComponent) {
        super.onBeforeShow(view)

        try {
            GlobalScreen.registerNativeHook()
            FX.primaryStage.setOnCloseRequest {
                GlobalScreen.unregisterNativeHook()
            }
        } catch (ex: NativeHookException) {
            logger.error("There was a problem registering the native hook.")
            logger.error(ex.message.toString())
            Platform.exit()
        }

        GlobalScreen.addNativeKeyListener(keyboardListener)
        GlobalScreen.addNativeMouseListener(mouseListener)
        GlobalScreen.addNativeMouseMotionListener(mouseListener)
        GlobalScreen.addNativeMouseWheelListener(mouseListener)
    }
}

object Context {
    val configuration: Configuration
    val windowName: String

    init {
        val mapper = ObjectMapper(YAMLFactory()).registerKotlinModule()
        val yaml = this.javaClass.getResource("/application.yml").readText()
        configuration = mapper.readValue(yaml, Configuration::class.java)
        windowName = "${configuration.info.name} - ${configuration.info.version}"
    }
}
package net.lethal.ghost.app

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.inject.Guice
import com.google.inject.Injector
import javafx.application.Platform
import net.lethal.ghost.config.Configuration
import net.lethal.ghost.controller.MainController
import net.lethal.ghost.service.LoggerService
import net.lethal.ghost.service.impl.NativeDispatchService
import net.lethal.ghost.style.Styles
import org.jnativehook.GlobalScreen
import org.jnativehook.NativeHookException
import tornadofx.*
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class GhostApp : App(MainController::class, Styles::class) {
    private val logger: LoggerService by Context.di()

    init {
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
    }
}

object Context {
    val guice: Injector
    val configuration: Configuration
    val windowName: String

    init {
        guice = Guice.createInjector(GhostModule())
        // Eagerly load all instances
        guice.allBindings.keys.forEach {
            guice.getInstance(it)
        }
        val mapper = ObjectMapper(YAMLFactory()).registerKotlinModule()
        val yaml = this.javaClass.getResource("/application.yml").readText()
        configuration = mapper.readValue(yaml, Configuration::class.java)
        windowName = "${configuration.info.name} - ${configuration.info.version}"
    }

    inline fun <reified T : Any> di(): ReadOnlyProperty<Any, T> = object : ReadOnlyProperty<Any, T> {
        var injected: T? = null
        override fun getValue(thisRef: Any, property: KProperty<*>): T {
            if (injected == null) injected = guice.getInstance(T::class.java)
            return injected!!
        }
    }
}
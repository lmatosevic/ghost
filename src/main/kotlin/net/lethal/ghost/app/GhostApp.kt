package net.lethal.ghost.app

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.inject.Guice
import net.lethal.ghost.config.Configuration
import net.lethal.ghost.controller.MainController
import net.lethal.ghost.style.Styles
import tornadofx.*
import kotlin.reflect.KClass

class GhostApp : App(MainController::class, Styles::class) {
    init {
        val guice = Guice.createInjector(GhostModule())

        FX.dicontainer = object : DIContainer {
            override fun <T : Any> getInstance(type: KClass<T>) = guice.getInstance(type.java)
        }
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
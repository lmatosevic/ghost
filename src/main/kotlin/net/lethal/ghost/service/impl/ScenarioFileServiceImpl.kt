package net.lethal.ghost.service.impl

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import net.lethal.ghost.app.Context
import net.lethal.ghost.event.Event
import net.lethal.ghost.service.CompressionService
import net.lethal.ghost.service.ScenarioFileService
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ScenarioFileServiceImpl : ScenarioFileService {

    private val mapper: ObjectMapper = ObjectMapper(JsonFactory()).registerKotlinModule()
    private val compressionService: CompressionService by Context.di()

    init {
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_CONCRETE_AND_ARRAYS)
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true)
    }

    override fun saveToFile(file: File, events: MutableList<Event>) {
        val serialized = mapper.writeValueAsString(Scenario(events))
        val compressed = compressionService.compress(serialized)
        FileOutputStream(file).write(compressed)
    }

    override fun loadFromFile(file: File): MutableList<Event> {
        val compressed = FileInputStream(file).readBytes()
        val serialized = compressionService.decompress(compressed)
        val scenario: Scenario = mapper.readValue(serialized)
        return scenario.events
    }

    private data class Scenario(val events: MutableList<Event>)
}


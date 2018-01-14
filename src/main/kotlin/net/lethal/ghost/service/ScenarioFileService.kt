package net.lethal.ghost.service

import net.lethal.ghost.event.Event
import java.io.File

interface ScenarioFileService {

    fun saveToFile(file: File, events: MutableList<Event>)

    fun loadFromFile(file: File): MutableList<Event>
}
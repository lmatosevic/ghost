package net.lethal.ghost.service

import net.lethal.ghost.event.Event
import java.io.File

interface ScenarioHolderService {

    fun execute()

    fun clear()

    fun save(file: File)

    fun load(file: File)

    fun removeLastClick(): Pair<Event?, Event?>
}
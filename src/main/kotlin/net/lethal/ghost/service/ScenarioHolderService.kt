package net.lethal.ghost.service

import net.lethal.ghost.event.Event

interface ScenarioHolderService {

    fun execute()

    fun clear()

    fun save()

    fun load()

    fun removeLastClick(): Pair<Event?, Event?>
}
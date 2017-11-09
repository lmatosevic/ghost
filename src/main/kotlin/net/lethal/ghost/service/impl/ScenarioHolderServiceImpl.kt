package net.lethal.ghost.service.impl

import net.lethal.ghost.app.Context
import net.lethal.ghost.event.Event
import net.lethal.ghost.event.EventSubscriber
import net.lethal.ghost.service.LoggerService
import net.lethal.ghost.service.ScenarioHolderService

class ScenarioHolderServiceImpl : ScenarioHolderService, EventSubscriber {
    private val events: MutableList<Event> = arrayListOf()

    private val logger: LoggerService by Context.di()
    private val keyboardListener: KeyboardListenerService by Context.di()
    private val mouseListener: MouseListenerService by Context.di()

    init {
        keyboardListener.addSubscriber(this)
        mouseListener.addSubscriber(this)
    }

    override fun onEvent(event: Event) {
        events.add(event)
    }

    override fun execute() {
        events.forEach {
            it.action.execute()
        }
    }

    override fun clear() {
        events.clear()
    }

    override fun save() {
        logger.info("Scenario saved as...")
    }

    override fun load() {
        logger.info("Scenario loaded...")
    }
}
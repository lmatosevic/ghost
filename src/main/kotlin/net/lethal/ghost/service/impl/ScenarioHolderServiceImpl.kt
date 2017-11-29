package net.lethal.ghost.service.impl

import net.lethal.ghost.app.Context
import net.lethal.ghost.event.Event
import net.lethal.ghost.event.EventSubscriber
import net.lethal.ghost.event.action.ActionType
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
            try {
                it.action.execute()
            } catch (e: IllegalArgumentException) {
                logger.error("Execution failed for action \"${it.action}\"")
            }
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

    override fun removeLastClick(): Pair<Event?, Event?> {
        var release: Event? = null
        var click: Event? = null
        if (events.size >= 2) {
            for (event in events.reversed()) {
                if (event.action.type != ActionType.MOUSE_PRESSED && event.action.type != ActionType.MOUSE_RELEASED) {
                    continue
                }
                if (release != null) {
                    click = event
                    break
                } else {
                    release = event
                }
            }
            events.remove(release)
            events.remove(click)
        }
        return Pair(release, click)
    }
}
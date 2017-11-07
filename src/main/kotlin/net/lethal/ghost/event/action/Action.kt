package net.lethal.ghost.event.action

interface Action {
    val type: ActionType

    fun execute()
}
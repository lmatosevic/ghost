package net.lethal.ghost.event

import net.lethal.ghost.event.type.KeyboardEvent
import net.lethal.ghost.event.type.MouseEvent

interface EventSubscriber {

    fun onEvent(event: Event) {}

    fun onKeyboardEvent(keyboardEvent: KeyboardEvent) {}

    fun onMouseEvent(mouseEvent: MouseEvent) {}
}
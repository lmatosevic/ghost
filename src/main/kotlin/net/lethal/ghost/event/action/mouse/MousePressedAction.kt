package net.lethal.ghost.event.action.mouse

import net.lethal.ghost.event.action.AbstractAction
import net.lethal.ghost.event.action.Action
import net.lethal.ghost.event.action.ActionType

class MousePressedAction : AbstractAction(), Action {
    override val type: ActionType = ActionType.MOUSE_PRESSED

    override fun execute() {
    }

    override fun toString(): String {
        return "Pressed"
    }
}
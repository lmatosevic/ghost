package net.lethal.ghost.event.action.mouse

import net.lethal.ghost.event.action.AbstractAction
import net.lethal.ghost.event.action.Action
import net.lethal.ghost.event.action.ActionType

class MouseDraggedAction : AbstractAction(), Action {
    override val type: ActionType = ActionType.MOUSE_DRAGGED

    override fun execute() {
    }

    override fun toString(): String {
        return "Dragged"
    }
}
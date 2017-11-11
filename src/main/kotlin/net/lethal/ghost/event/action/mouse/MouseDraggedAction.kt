package net.lethal.ghost.event.action.mouse

import net.lethal.ghost.event.action.AbstractAction
import net.lethal.ghost.event.action.Action
import net.lethal.ghost.event.action.ActionType

class MouseDraggedAction(private val x: Int, private val y: Int) : AbstractAction(), Action {
    override val type: ActionType = ActionType.MOUSE_DRAGGED

    override fun execute() {
        robot.mouseMove(x, y)
        robot.delay(3)
    }

    override fun toString(): String {
        return "Dragged to ($x, $y)"
    }
}
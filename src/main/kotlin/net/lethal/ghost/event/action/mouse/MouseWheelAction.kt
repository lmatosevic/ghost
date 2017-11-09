package net.lethal.ghost.event.action.mouse

import net.lethal.ghost.event.action.AbstractAction
import net.lethal.ghost.event.action.Action
import net.lethal.ghost.event.action.ActionType

class MouseWheelAction(private val rotation: Int) : AbstractAction(), Action {
    override val type: ActionType = ActionType.MOUSE_WHEEL

    override fun execute() {
        robot.mouseWheel(rotation)
        robot.delay(5)
    }

    override fun toString(): String {
        return "Wheel moved by $rotation"
    }
}
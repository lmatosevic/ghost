package net.lethal.ghost.event.action.mouse

import net.lethal.ghost.event.action.AbstractAction
import net.lethal.ghost.event.action.Action
import net.lethal.ghost.event.action.ActionType
import java.io.Serializable

class MouseWheelAction(private val rotation: Int) : AbstractAction(), Action, Serializable {
    override val type: ActionType = ActionType.MOUSE_WHEEL

    override fun execute() {
        robot.mouseWheel(rotation)
        robot.delay(30)
    }

    fun getRotation(): Int {
        return rotation
    }

    override fun toString(): String {
        return "Wheel moved by $rotation"
    }
}
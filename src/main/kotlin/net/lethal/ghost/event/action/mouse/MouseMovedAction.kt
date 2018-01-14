package net.lethal.ghost.event.action.mouse

import net.lethal.ghost.event.action.AbstractAction
import net.lethal.ghost.event.action.Action
import net.lethal.ghost.event.action.ActionType
import java.io.Serializable

class MouseMovedAction(private val x: Int, private val y: Int) : AbstractAction(), Action, Serializable {
    override val type: ActionType = ActionType.MOUSE_MOVED

    override fun execute() {
        robot.mouseMove(x, y)
        robot.delay(3)
    }

    fun getX() : Int {
        return x
    }

    fun getY(): Int {
        return y
    }

    override fun toString(): String {
        return "Moved to ($x, $y)"
    }
}
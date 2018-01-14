package net.lethal.ghost.event.action.mouse

import net.lethal.ghost.event.action.AbstractAction
import net.lethal.ghost.event.action.Action
import net.lethal.ghost.event.action.ActionType
import java.io.Serializable

class MouseReleasedAction(private val button: Int) : AbstractAction(), Action, Serializable {
    override val type: ActionType = ActionType.MOUSE_RELEASED

    override fun execute() {
        if (button > 0) {
            robot.mouseRelease(button)
            robot.delay(50)
        }
    }

    fun getButton(): Int {
        return button
    }

    override fun toString(): String {
        return "Released button $button"
    }
}
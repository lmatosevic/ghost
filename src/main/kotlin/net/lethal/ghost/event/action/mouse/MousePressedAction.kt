package net.lethal.ghost.event.action.mouse

import net.lethal.ghost.event.action.AbstractAction
import net.lethal.ghost.event.action.Action
import net.lethal.ghost.event.action.ActionType

class MousePressedAction(private val button: Int) : AbstractAction(), Action {
    override val type: ActionType = ActionType.MOUSE_PRESSED

    override fun execute() {
        if (button > 0) {
            robot.mousePress(button)
            robot.delay(50)
        }
    }

    override fun toString(): String {
        return "Pressed button $button"
    }
}
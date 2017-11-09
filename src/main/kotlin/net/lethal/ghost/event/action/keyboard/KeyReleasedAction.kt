package net.lethal.ghost.event.action.keyboard

import net.lethal.ghost.event.action.AbstractAction
import net.lethal.ghost.event.action.Action
import net.lethal.ghost.event.action.ActionType

class KeyReleasedAction(private val rawKeyCode: Int) : AbstractAction(), Action {
    override val type: ActionType = ActionType.KEY_RELEASED

    override fun execute() {
        robot.keyRelease(rawKeyCode)
        robot.delay(50)
    }

    override fun toString(): String {
        return "Key $rawKeyCode released"
    }
}
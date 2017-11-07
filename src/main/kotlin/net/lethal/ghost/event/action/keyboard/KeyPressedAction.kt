package net.lethal.ghost.event.action.keyboard

import net.lethal.ghost.event.action.AbstractAction
import net.lethal.ghost.event.action.Action
import net.lethal.ghost.event.action.ActionType

class KeyPressedAction(private val keyCode: Int?) : AbstractAction(), Action {
    override val type: ActionType = ActionType.KEY_PRESSED

    override fun execute() {
        if (keyCode != null) {
            robot.keyPress(keyCode)
        }
    }

    override fun toString(): String {
        return "Key $keyCode pressed"
    }
}
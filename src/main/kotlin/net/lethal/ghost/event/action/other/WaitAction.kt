package net.lethal.ghost.event.action.other

import net.lethal.ghost.event.action.AbstractAction
import net.lethal.ghost.event.action.Action
import net.lethal.ghost.event.action.ActionType

class WaitAction(private val delay: Int) : AbstractAction(), Action {
    override val type: ActionType = ActionType.WAIT

    override fun execute() {
        robot.delay(delay)
    }

    override fun toString(): String {
        return "Wait $delay ms"
    }
}
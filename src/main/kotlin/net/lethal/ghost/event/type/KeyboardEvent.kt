package net.lethal.ghost.event.type

import net.lethal.ghost.event.DeviceType
import net.lethal.ghost.event.Event
import net.lethal.ghost.event.action.Action
import java.io.Serializable

class KeyboardEvent(override val order: Int, override val duration: Long,
                    override val action: Action, override val device: DeviceType = DeviceType.KEYBOARD) : Event, Serializable
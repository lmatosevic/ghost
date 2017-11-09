package net.lethal.ghost.event.type

import net.lethal.ghost.event.DeviceType
import net.lethal.ghost.event.Event
import net.lethal.ghost.event.action.Action

class MouseEvent(override val order: Int, override val duration: Long,
                 override val action: Action, override val device: DeviceType = DeviceType.MOUSE) : Event
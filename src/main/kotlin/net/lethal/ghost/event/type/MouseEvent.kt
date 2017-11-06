package net.lethal.ghost.event.type

import net.lethal.ghost.event.DeviceType
import net.lethal.ghost.event.Event
import net.lethal.ghost.event.action.Action
import java.util.*

class MouseEvent(override val timeStart: Date, override val timeEnd: Date,
                 override val action: Action, override val device: DeviceType = DeviceType.MOUSE) : Event
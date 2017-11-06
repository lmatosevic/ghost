package net.lethal.ghost.event

import net.lethal.ghost.event.action.Action
import java.util.*

interface Event {
    val timeStart: Date

    val timeEnd: Date

    val action: Action

    val device: DeviceType
}
package net.lethal.ghost.event

import net.lethal.ghost.event.action.Action

interface Event {
    val order: Int

    val duration: Long

    val action: Action

    val device: DeviceType
}
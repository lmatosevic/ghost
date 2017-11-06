package net.lethal.ghost.event

interface EventProvider {
    var subscribers: MutableList<EventSubscriber>

    fun addSubscriber(subscriber: EventSubscriber)

    fun removeSubscriber(subscriber: EventSubscriber)

    fun notifySubscribers(event: Event)
}
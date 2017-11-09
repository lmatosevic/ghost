package net.lethal.ghost.component

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import net.lethal.ghost.event.Event
import tornadofx.*

class TableInteractionComponent(eventTable: TableView<Event>, orderColumn: TableColumn<Event, Int>,
                                durationColumn: TableColumn<Event, Long>, deviceColumn: TableColumn<Event, String>,
                                actionColumn: TableColumn<Event, String>) : Component() {
    private val rows: ObservableList<Event> = FXCollections.observableArrayList()

    init {
        orderColumn.cellValueFactory = PropertyValueFactory<Event, Int>("order")
        durationColumn.cellValueFactory = PropertyValueFactory<Event, Long>("duration")
        deviceColumn.cellValueFactory = PropertyValueFactory<Event, String>("device")
        actionColumn.cellValueFactory = PropertyValueFactory<Event, String>("action")
        eventTable.items = rows
    }

    fun addRow(event: Event) {
        rows.add(event)
    }

    fun clearRows() {
        rows.clear()
    }
}
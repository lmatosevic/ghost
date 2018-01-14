package net.lethal.ghost.controller

import javafx.scene.control.Button
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import net.lethal.ghost.app.Context
import net.lethal.ghost.component.ButtonInteractionComponent
import net.lethal.ghost.component.ImageInteractionComponent
import net.lethal.ghost.component.TableInteractionComponent
import net.lethal.ghost.event.Event
import net.lethal.ghost.event.EventSubscriber
import net.lethal.ghost.service.LoggerService
import net.lethal.ghost.service.ScenarioHolderService
import net.lethal.ghost.service.impl.KeyboardListenerService
import net.lethal.ghost.service.impl.MouseListenerService
import tornadofx.*
import java.io.File

class MainController : View(Context.windowName), EventSubscriber {
    override val root: VBox = loadFXML("/views/MainView.fxml", true)

    private val keyboardImg: ImageView by fxid()
    private val mouseImg: ImageView by fxid()
    private val playBtn: Button by fxid()
    private val pauseBtn: Button by fxid()
    private val stopBtn: Button by fxid()
    private val recordBtn: Button by fxid()
    private val saveBtn: Button by fxid()
    private val openBtn: Button by fxid()

    private val eventTable: TableView<Event> by fxid()
    private val orderColumn: TableColumn<Event, Int> by fxid()
    private val durationColumn: TableColumn<Event, Long> by fxid()
    private val deviceColumn: TableColumn<Event, String> by fxid()
    private val actionColumn: TableColumn<Event, String> by fxid()

    private val logger: LoggerService by Context.di()
    private val keyboardListener: KeyboardListenerService by Context.di()
    private val mouseListener: MouseListenerService by Context.di()
    private val scenarioHolder: ScenarioHolderService by Context.di()

    private val imageInteractionComponent: ImageInteractionComponent
    private val buttonInteractionComponent: ButtonInteractionComponent
    private val tableInteractionComponent: TableInteractionComponent

    private var lastClickRemoved: Boolean = false

    init {
        keyboardListener.addSubscriber(this)
        mouseListener.addSubscriber(this)
        imageInteractionComponent = ImageInteractionComponent(keyboardImg, mouseImg)
        buttonInteractionComponent = ButtonInteractionComponent(playBtn, pauseBtn, stopBtn, recordBtn, saveBtn, openBtn)
        tableInteractionComponent = TableInteractionComponent(eventTable, orderColumn, durationColumn, deviceColumn, actionColumn)
    }

    override fun onEvent(event: Event) {
        imageInteractionComponent.changeImageColor(event.device)
        tableInteractionComponent.addRow(event)
    }

    fun play() {
        if (keyboardListener.paused && mouseListener.paused) {
            keyboardListener.start()
            mouseListener.start()
            lastClickRemoved = false
        }
        if (!keyboardListener.started && !mouseListener.started) {
            runAsync {
                scenarioHolder.execute()
            }
        }
    }

    fun pause() {
        removeLastClick()
        if (keyboardListener.started && mouseListener.started) {
            keyboardListener.pause()
            mouseListener.pause()
        }
    }

    fun stop() {
        removeLastClick()
        keyboardListener.stop()
        mouseListener.stop()
    }

    fun record() {
        if (!keyboardListener.started && !mouseListener.started) {
            scenarioHolder.clear()
            tableInteractionComponent.clearRows()
        }
        keyboardListener.start()
        mouseListener.start()
        lastClickRemoved = false
    }

    fun save() {
        val file = openFileChooseDialog("Save scenario file", FileChooserMode.Save)
        if (file != null) {
            scenarioHolder.save(file)
        }
    }

    fun open() {
        val file = openFileChooseDialog("Open scenario file", FileChooserMode.Single)
        if (file != null) {
            scenarioHolder.load(file)
        }
    }

    private fun openFileChooseDialog(title: String, mode: FileChooserMode): File? {
        val filters = arrayOf(FileChooser.ExtensionFilter("Ghost file", "*.gh"),
                FileChooser.ExtensionFilter("All files", "*.*"))
        val files: List<File> = chooseFile(title, filters, mode)
        return files.getOrNull(0)
    }

    private fun removeLastClick() {
        if (!lastClickRemoved) {
            val lastClick = scenarioHolder.removeLastClick()
            tableInteractionComponent.removeLastClick(lastClick.first, lastClick.second)
            lastClickRemoved = true
        }
    }
}

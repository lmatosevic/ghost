package net.lethal.ghost.controller

import javafx.scene.control.Label
import javafx.scene.layout.VBox
import tornadofx.*

class MainController : View("Sample app") {
    override val root: VBox = loadFXML("/views/MainView.fxml", true)

    private val nameLabel: Label by fxid()

    fun changeName() {
        nameLabel.text = "new value"
    }
}

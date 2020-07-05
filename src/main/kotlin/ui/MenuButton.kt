package ui

import javafx.scene.control.Button
import tornadofx.*

class MenuButton : Button() {
    val selectedProperty = booleanProperty().apply {
        onChange {
            if (it)
                addPseudoClass("selected")
            else
                removePseudoClass("selected")
        }
    }
    var isSelected: Boolean by selectedProperty

    init {
        addClass("toolbar-button")
    }

    companion object {
        init {
            importStylesheet("/stylesheets/toolbar.css")
        }
    }
}
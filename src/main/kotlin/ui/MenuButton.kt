package ui

import javafx.scene.control.Button
import tornadofx.*

class MenuButton : Button() {
    val activeProperty = booleanProperty().apply {
        onChange {
            if (it)
                addPseudoClass("active")
            else
                removePseudoClass("active")
        }
    }
    var isActive: Boolean by activeProperty

    init {
        addClass("toolbar-button")
    }

    companion object {
        init {
            importStylesheet("/stylesheets/toolbar.css")
        }
    }
}
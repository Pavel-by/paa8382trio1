package ui

import javafx.beans.property.SimpleBooleanProperty
import javafx.stage.Stage
import model.NodeModel
import model.TreeModel
import tornadofx.App
import tornadofx.Workspace

class TrioApp : App(TrioWorkspace::class) {
    override fun start(stage: Stage) {
        super.start(stage)

        stage.isMaximized = true
    }
}
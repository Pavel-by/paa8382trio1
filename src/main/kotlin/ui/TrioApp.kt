package ui

import javafx.stage.Stage
import tornadofx.App

class TrioApp : App(TrioWorkspace::class) {
    override fun start(stage: Stage) {
        super.start(stage)

        stage.isMaximized = true
    }
}
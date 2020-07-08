package ui.controller

import javafx.scene.control.Alert
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import javafx.stage.FileChooser
import model.EdgeModel
import model.NodeModel
import tornadofx.FileChooserMode
import tornadofx.chooseFile
import utils.FileStorager
import java.io.File

class StateSave : ControllerState() {
    override fun onStart() {
        super.onStart()
        val chooser = FileChooser()
        chooser.title = "Сохранить граф"
        chooser.extensionFilters.addAll(
            arrayOf(
                FileChooser.ExtensionFilter("Граф в json формате", "*.trio, *.json"),
                FileChooser.ExtensionFilter("Все файлы", "*.*")
            )
        )
        chooser.initialFileName = "tree.json"
        val file = chooser.showSaveDialog(controller.treeView.currentWindow)

        if (file != null) {
            val storager = FileStorager()
            if (!storager.write(controller.treeView, file)) {
                val alert = Alert(Alert.AlertType.ERROR)
                alert.contentText = storager.error
                alert.show()
            }
        }

        controller.state = StateIdle()
    }

    override fun onNodeClick(node: NodeModel, event: MouseEvent) {}

    override fun onEdgeClick(edge: EdgeModel, event: MouseEvent) {}

    override fun onFieldClick(event: MouseEvent) {}

    override fun onAddNodeButtonClick() {}

    override fun onAddEdgeButtonClick() {}

    override fun onEditButtonClick() {}

    override fun onForwardButtonClick() {}

    override fun onForceForwardButtonClick() {}

    override fun onBackButtonClick() {}

    override fun onForceBackButtonClick() {}

    override fun onGenerateGraphButtonClick() {}

    override fun onRootKeyPressed(event: KeyEvent) {}

    override fun onSaveButtonClick() {}

    override fun onOpenButtonClick() {}
}
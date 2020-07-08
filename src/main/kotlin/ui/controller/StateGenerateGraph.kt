package ui.controller

import javafx.scene.control.Alert
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import model.EdgeModel
import model.GraphGenerator
import model.NodeModel
import tornadofx.isDouble
import tornadofx.isInt
import ui.GraphGeneratorView

class StateGenerateGraph : ControllerState() {
    var modal: Stage? = null

    override fun onStart() {
        super.onStart()
        controller.isEdit = true
        controller.isAddNodeSelected = false
        controller.isAddEdgeSelected = false

        val generatorView = GraphGeneratorView()
        generatorView.action = {
            var valid = true

            if (!generatorView.nodesField.text.isInt()) {
                valid = false
                generatorView.nodesField.text = "0"
            }

            if (!generatorView.edgesField.text.isInt()) {
                valid = false
                generatorView.edgesField.text = "0"
            }

            if (valid) {
                modal?.close()
                val generator = GraphGenerator()
                generator.process(
                    controller.treeView,
                    generatorView.nodesField.text.toInt(),
                    generatorView.edgesField.text.toInt()
                )

                if (generator.hasError()) {
                    val alert = Alert(Alert.AlertType.ERROR)
                    alert.headerText = "Ошибка генерации"
                    alert.contentText = generator.errorMessage
                    alert.show()
                }
            }
        }

        generatorView.openModal()?.apply {
            modal = this

            setOnHidden {
                modal = null
                controller.state = StateIdle()
            }
        }

        if (modal == null)
            controller.state = StateIdle()
    }

    override fun onStop() {
        super.onStop()
        controller.selectedEdges.clear()
        controller.selectedNodes.clear()
        modal?.close()
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
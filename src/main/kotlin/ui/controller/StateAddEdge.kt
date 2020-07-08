package ui.controller

import javafx.collections.SetChangeListener
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import model.EdgeModel
import model.NodeModel
import tornadofx.isDouble
import ui.SimpleEditor

class StateAddEdge : ControllerState() {
    private val nodesChangedListener = SetChangeListener<NodeModel> {
        if (it.set.size >= 2)
            addEdge(it.set.first(), it.set.last())
    }
    var modal: Stage? = null

    override fun onStart() {
        super.onStart()
        controller.isEdit = true
        controller.isAddNodeSelected = false
        controller.isAddEdgeSelected = true
        controller.selectedEdges.clear()

        if (controller.selectedNodes.size == 2)
            addEdge(controller.selectedNodes.first(), controller.selectedNodes.last())
        else {
            if (controller.selectedNodes.size > 2)
                controller.selectedNodes.clear()

            controller.selectedNodes.addListener(nodesChangedListener)
        }
    }

    override fun onStop() {
        super.onStop()
        controller.isAddEdgeSelected = false
        controller.selectedNodes.removeListener(nodesChangedListener)
    }

    override fun onNodeClick(node: NodeModel, event: MouseEvent) {
        controller.selectedNodes.add(node)
        event.consume()
    }

    override fun onEdgeClick(edge: EdgeModel, event: MouseEvent) {
        event.consume()
    }

    override fun onFieldClick(event: MouseEvent) {
        controller.selectedNodes.clear()
        event.consume()
    }

    override fun onAddNodeButtonClick() {
        controller.state = StateAddNode()
    }

    override fun onAddEdgeButtonClick() {
        controller.state = StateIdle()
    }

    override fun onRootKeyPressed(event: KeyEvent) { }

    private fun addEdge(first: NodeModel, second: NodeModel) {
        val editor = SimpleEditor("Добавить ребро")
        editor.onOk = onOkLambda@{
            if (!editor.text.isDouble()) {
                editor.errorText = "Значение можнт иметь только тип Double, например '12.48'."
                return@onOkLambda
            }

            val edge = EdgeModel(first, second, editor.text.toDouble())
            controller.tree.edges.add(edge)
            controller.selectedNodes.clear()
            editor.currentStage?.close()
        }

        editor.onCancel = {
            editor.currentStage?.close()
        }

        editor.text = "0"

        editor.textField?.textProperty()?.addListener { value, prevValue, newValue ->
            if (newValue.toDoubleOrNull() == null)
                editor.text = prevValue
        }

        modal = editor.openModal(
            escapeClosesWindow = true,
            resizable = false
        )

        if (modal == null) {
            println("StateEditNode: Cannot open modal window.")
            controller.state = StateIdle()
        } else {
            modal!!.setOnHidden {
                this@StateAddEdge.modal = null

                if (isStateAttached)
                    controller.state = StateIdle()
            }
        }
    }

    override fun onEditButtonClick() {
        controller.state = StateAlgorithm()
    }

    override fun onForwardButtonClick() {}

    override fun onBackButtonClick() {}

    override fun onForceForwardButtonClick() {}

    override fun onForceBackButtonClick() {}

    override fun onGenerateGraphButtonClick() {
        controller.state = StateGenerateGraph()
    }

    override fun onSaveButtonClick() {
        controller.state = StateSave()
    }

    override fun onOpenButtonClick() {
        controller.state = StateOpen()
    }
}
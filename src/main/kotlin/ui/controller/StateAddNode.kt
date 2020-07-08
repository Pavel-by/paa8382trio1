package ui.controller

import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import model.EdgeModel
import model.NodeModel
import ui.SimpleEditor

class StateAddNode : ControllerState() {
    var modal: Stage? = null

    override fun onStart() {
        super.onStart()
        controller.isEdit = true
        controller.isAddNodeSelected = true
        controller.isAddEdgeSelected = false
    }

    override fun onStop() {
        super.onStop()
        controller.isAddNodeSelected = false
    }

    override fun onNodeClick(node: NodeModel, event: MouseEvent) { }

    override fun onEdgeClick(edge: EdgeModel, event: MouseEvent) { }

    override fun onFieldClick(event: MouseEvent) {
        val x = event.x + controller.treeView.scrollX
        val y = event.y + controller.treeView.scrollY
        val editor = SimpleEditor("Добавить вершину")

        editor.onOk = onOkLambda@{
            val errorText = validateNodeName(editor.text)

            if (errorText != null) {
                editor.errorText = errorText
                return@onOkLambda
            }

            val node = NodeModel(editor.text)
            node.name = editor.text
            controller.treeView.addNode(node).also { view ->
                view.x = x
                view.y = y
            }
            editor.currentStage?.close()
        }

        editor.onCancel = {
            editor.currentStage?.close()
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
                this@StateAddNode.modal = null

                if (isStateAttached)
                    controller.state = StateIdle()
            }
        }
    }

    private fun validateNodeName(name: String): String? {
        if (controller.tree.findNodeByName(name) != null)
            return "Невозможно применить имя <$name> к вершине. Вершина с таким " +
                    "именем уже существует."

        if (name.isEmpty())
            return "Имя вершины не может быть пустым"

        return null
    }

    override fun onAddNodeButtonClick() {
        controller.state = StateIdle()
    }

    override fun onAddEdgeButtonClick() {
        controller.state = StateAddEdge()
    }

    override fun onRootKeyPressed(event: KeyEvent) { }

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
}
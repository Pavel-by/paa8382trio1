package ui.controller

import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import model.EdgeModel
import model.NodeModel
import ui.SimpleEditor

class StateEditNode(val node: NodeModel) : ControllerState() {
    var modal: Stage? = null

    override fun onStart() {
        super.onStart()
        val editor = SimpleEditor("Редактирование вершины")
        editor.text = node.name

        editor.onOk = onOkLambda@{
            val errorText = validateNodeName(editor.text)

            if (errorText != null) {
                editor.errorText = errorText
                return@onOkLambda
            }

            node.name = editor.text
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
                this@StateEditNode.modal = null

                if (isStateAttached)
                    controller.state = StateIdle()
            }
        }
    }

    private fun validateNodeName(name: String): String? {
        if (node.name != name && controller.tree.findNodeByName(name) != null)
            return "Невозможно применить имя <$name> к вершине. Вершина с таким " +
                    "именем уже существует."

        if (name.isEmpty())
            return "Имя вершины не может быть пустым"

        return null
    }

    override fun onStop() {
        super.onStop()
        modal?.close()
    }

    override fun onNodeClick(node: NodeModel, event: MouseEvent) {
        throw IllegalCallerException()
    }

    override fun onEdgeClick(edge: EdgeModel, event: MouseEvent) {
        throw IllegalCallerException()
    }

    override fun onFieldClick(event: MouseEvent) {
        throw IllegalCallerException()
    }

    override fun onAddNodeButtonClick() {
        throw IllegalCallerException()
    }

    override fun onAddEdgeButtonClick() {
        throw IllegalCallerException()
    }

    override fun onRootKeyPressed(event: KeyEvent) { }

    override fun onEditButtonClick() {
        controller.state = StateAlgorithm()
    }

    override fun onForwardButtonClick() {}

    override fun onBackButtonClick() {}

    override fun onForceForwardButtonClick() {}

    override fun onForceBackButtonClick() {}

    override fun onGenerateGraphButtonClick() {}
}
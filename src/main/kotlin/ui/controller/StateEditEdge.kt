package ui.controller

import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import model.EdgeModel
import model.NodeModel
import tornadofx.isDouble
import ui.SimpleEditor

class StateEditEdge(val edge: EdgeModel) : ControllerState() {
    var modal: Stage? = null

    override fun onStart() {
        super.onStart()
        val editor = SimpleEditor("Редактировать ребро")
        editor.onOk = onOkLambda@{
            if (!editor.text.isDouble()) {
                editor.errorText = "Значение можнт иметь только тип Double, например '12.48'."
                return@onOkLambda
            }

            edge.length = editor.text.toDouble()
            editor.currentStage?.close()
        }

        editor.onCancel = {
            editor.currentStage?.close()
        }

        editor.text = edge.length.toString()

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
                this@StateEditEdge.modal = null

                if (isStateAttached)
                    controller.state = StateIdle()
            }
        }
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
}
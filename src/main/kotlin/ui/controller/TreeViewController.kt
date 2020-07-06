package ui.controller

import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import model.EdgeModel
import model.NodeModel
import model.TreeModel
import tornadofx.*
import ui.views.TreeView

class TreeViewController(val treeView: TreeView) {
    val currentStepProperty = longProperty(-1).apply {
        onChange { step ->
            selectedEdges.removeIf { it.step > step || it.step == -1 }
            selectedEdges.addAll(tree.edges.filter { it.step <= step && it.step != -1 })
        }
    }
    var currentStep by currentStepProperty

    val maxStepProperty = longProperty(-1).apply {
        onChange {
            if (it < currentStep)
                currentStep = it
        }
    }
    var maxStep by maxStepProperty

    val editProperty = booleanProperty(false)
    var isEdit by editProperty

    val addNodeSelectedProperty = booleanProperty(false)
    var isAddNodeSelected by addNodeSelectedProperty

    val addEdgeSelectedProperty = booleanProperty(false)
    var isAddEdgeSelected by addEdgeSelectedProperty

    val selectedNodes = observableSetOf<NodeModel>()
    val selectedEdges = observableSetOf<EdgeModel>()

    val tree: TreeModel
        get() = treeView.treeModel!!

    var state: ControllerState = StateIdle().apply { attach(this@TreeViewController) }
        set(value) {
            field.detach()
            field = value
            field.attach(this)
        }

    fun onNodeClick(node: NodeModel, event: MouseEvent) {
        state.onNodeClick(node, event)
    }

    fun onEdgeClick(edge: EdgeModel, event: MouseEvent) {
        state.onEdgeClick(edge, event)
    }

    fun onFieldClick(event: MouseEvent) {
        state.onFieldClick(event)
    }

    fun onAddNodeButtonClick() {
        state.onAddNodeButtonClick()
    }

    fun onAddEdgeButtonClick() {
        state.onAddEdgeButtonClick()
    }

    fun onRootKeyPressed(event: KeyEvent) {
        state.onRootKeyPressed(event)
    }

    fun onEditButtonClick() {
        state.onEditButtonClick()
    }

    fun onForwardButtonClick() {
        state.onForwardButtonClick()
    }

    fun onBackButtonClick() {
        state.onBackButtonClick()
    }
}
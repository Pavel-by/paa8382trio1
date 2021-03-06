package ui.controller

import javafx.beans.property.IntegerProperty
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import model.BoruvkaAlgorithm
import model.EdgeModel
import model.NodeModel
import tornadofx.*
import java.lang.Long.max

class StateAlgorithm : ControllerState() {
    override fun onStart() {
        super.onStart()
        controller.isEdit = false
        controller.isAddEdgeSelected = false
        controller.isAddNodeSelected = false
        BoruvkaAlgorithm().process(controller.tree)
        controller.selectedNodes.addAll(controller.tree.nodes)
        controller.selectedEdges.clear()
        controller.currentStep = -1
        controller.maxStep = controller.tree.edges.fold(
            -1L,
            {maxStep, model -> max(maxStep, model.step.toLong())}
        )
    }

    override fun onStop() {
        super.onStop()
        controller.selectedNodes.clear()
        controller.selectedEdges.clear()
    }

    override fun onNodeClick(node: NodeModel, event: MouseEvent) {}

    override fun onEdgeClick(edge: EdgeModel, event: MouseEvent) {}

    override fun onFieldClick(event: MouseEvent) {}

    override fun onAddNodeButtonClick() {}

    override fun onAddEdgeButtonClick() {}

    override fun onEditButtonClick() {
        controller.state = StateIdle()
    }

    override fun onForwardButtonClick() {
        if (controller.currentStep >= controller.maxStep)
            return

        controller.currentStep += 1
    }

    override fun onBackButtonClick() {
        if (controller.currentStep < 0)
            return

        controller.currentStep -= 1
    }

    override fun onRootKeyPressed(event: KeyEvent) {}

    override fun onForceForwardButtonClick() {
        controller.currentStep = controller.maxStep
    }

    override fun onForceBackButtonClick() {
        controller.currentStep = -1
    }

    override fun onGenerateGraphButtonClick() {}

    override fun onSaveButtonClick() {}

    override fun onOpenButtonClick() {}
}
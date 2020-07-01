package ui.controller

import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import model.EdgeModel
import model.NodeModel

class StateIdle : ControllerState() {
    override fun onStart() {
        super.onStart()
        controller.isAddEdgeSelected = false
        controller.isAddNodeSelected = false
    }

    override fun onNodeClick(node: NodeModel, event: MouseEvent) {
        if (event.clickCount == 2) {
            event.consume()
            controller.state = StateEditNode(node)
            return
        }

        if (event.isShiftDown || event.isControlDown) {
            if (controller.selectedNodes.contains(node))
                controller.selectedNodes.remove(node)
            else
                controller.selectedNodes.add(node)
        } else {
            controller.selectedNodes.removeIf { it != node }
            controller.selectedNodes.add(node)
        }

        event.consume()
    }

    override fun onEdgeClick(edge: EdgeModel, event: MouseEvent) {
        TODO("Not yet implemented")
    }

    override fun onFieldClick(event: MouseEvent) {
        controller.selectedNodes.clear()
        event.consume()
    }

    override fun onAddNodeButtonClick() {
        controller.state = StateAddNode()
    }

    override fun onAddEdgeButtonClick() {
        TODO("Not yet implemented")
    }

    override fun onRootKeyPressed(event: KeyEvent) {
        if (event.code == KeyCode.DELETE) {
            for (node in controller.selectedNodes) {
                controller.tree.removeNode(node)
            }
            controller.selectedNodes.clear()
        }
    }
}
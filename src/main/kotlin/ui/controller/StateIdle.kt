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
            controller.selectedEdges.clear()
            controller.selectedNodes.add(node)
        }

        event.consume()
    }

    override fun onEdgeClick(edge: EdgeModel, event: MouseEvent) {
        if (event.clickCount == 2) {
            controller.state = StateEditEdge(edge)
        }

        if (event.isShiftDown || event.isControlDown) {
            if (controller.selectedEdges.contains(edge))
                controller.selectedEdges.remove(edge)
            else
                controller.selectedEdges.add(edge)
        } else {
            controller.selectedEdges.removeIf { it != edge }
            controller.selectedNodes.clear()
            controller.selectedEdges.add(edge)
        }
        event.consume()
    }

    override fun onFieldClick(event: MouseEvent) {
        controller.selectedNodes.clear()
        controller.selectedEdges.clear()
        event.consume()
    }

    override fun onAddNodeButtonClick() {
        controller.state = StateAddNode()
    }

    override fun onAddEdgeButtonClick() {
        controller.state = StateAddEdge()
    }

    override fun onRootKeyPressed(event: KeyEvent) {
        if (event.code == KeyCode.DELETE) {
            for (edge in controller.selectedEdges)
                controller.tree.edges.remove(edge)
            for (node in controller.selectedNodes)
                controller.tree.nodes.remove(node)

            controller.selectedNodes.clear()
            controller.selectedEdges.clear()
        }
    }
}
package ui.views

import javafx.collections.ListChangeListener
import javafx.scene.layout.Pane
import model.NodeModel
import model.TreeModel
import tornadofx.*
import ui.controller.TreeViewController

class TreeView : View("Tree") {
    private val nodesChangeListener = ListChangeListener<NodeModel> { change ->
        while (change.next()) {
            for (node in change.addedSubList) {
                addNode(node)
            }

            for (node in change.removed) {
                removeNode(node)
            }
        }
    }
    lateinit var nodesPane: Pane
    var controller: TreeViewController? = null

    private val nodesViews = arrayListOf<NodeView>()
    var treeModel: TreeModel? = null
        private set

    val scrollX: Double
        get() = (nodesPane.width - root.width) * root.hvalue

    val scrollY: Double
        get() = (nodesPane.height - root.height) * root.vvalue

    override val root = scrollpane {
        minWidth = 300.0
        minHeight = 300.0

        isFitToHeight = true
        isFitToWidth = true

        isPannable = false

        addClass("trio-scrollpane")

        setOnMouseClicked {
            controller?.onFieldClick(it)
        }

        nodesPane = pane {
            this.minWidth = 2000.0
            this.minHeight = 2000.0
            this.fitToParentSize()
        }
    }

    fun setNodeLayoutX(node: NodeModel, x: Double) {
        nodesViews.find { it.node == node }?.x = x
    }

    fun setNodeLayoutY(node: NodeModel, y: Double) {
        nodesViews.find { it.node == node }?.y = y
    }

    fun relocateNode(node: NodeModel, x: Double, y: Double) {
        nodesViews.find { it.node == node }?.also {view ->
            view.x = x
            view.y = y
        }
    }

    fun addNode(node: NodeModel): NodeView {
        var nodeView = nodesViews.find { it.node == node }

        if (nodeView != null)
            return nodeView

        nodeView = NodeView(node)
        nodesViews.add(nodeView)
        nodesPane.add(nodeView)
        nodeView.attach(this)

        if (!node.isInTree)
            treeModel!!.addNode(node)

        return nodeView
    }

    private fun removeNode(node: NodeModel) {
        val nodeView = nodesViews.find { it.node == node } ?: return
        nodesViews.remove(nodeView)
        nodeView.detach()
    }

    fun attach(treeModel: TreeModel) {
        this.treeModel = treeModel
        treeModel.nodes.addListener(nodesChangeListener)

        for (node in treeModel.nodes)
            addNode(node)
    }

    fun detach() {
        treeModel!!.nodes.removeListener(nodesChangeListener)

        for (node in treeModel!!.nodes)
            removeNode(node)
        removeFromParent()
    }

    companion object {
        init {
            importStylesheet("/stylesheets/scrollpane.css")
        }
    }
}

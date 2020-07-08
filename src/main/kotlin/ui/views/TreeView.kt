package ui.views

import javafx.collections.SetChangeListener
import javafx.scene.layout.Pane
import model.EdgeModel
import model.NodeModel
import model.TreeModel
import tornadofx.*
import ui.controller.TreeViewController

class TreeView : View("Tree") { 
    private val nodesChangeListener = SetChangeListener<NodeModel> { change ->
        if (change.wasAdded())
            addNode(change.elementAdded)
        else
            removeNode(change.elementRemoved)
    }
    private val edgesChangedListener = SetChangeListener<EdgeModel> { change ->
        if (change.wasAdded())
            addEdge(change.elementAdded)
        else
            removeEdge(change.elementRemoved)
    }
    lateinit var nodesPane: Pane
    lateinit var edgesPane: Pane
    var controller: TreeViewController? = null

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

        stackpane {
            this.minWidth = 2000.0
            this.minHeight = 2000.0

            edgesPane = pane {
                isPickOnBounds = false
                fitToParentSize()
            }

            nodesPane = pane {
                isPickOnBounds = false
                fitToParentSize()
            }
        }
    }

    fun addNode(node: NodeModel): NodeView {
        if (node.view != null)
            return node.view!!

        if (!node.isInTree) {
            treeModel!!.nodes.add(node)
            return node.view!!
        }

        val nodeView = NodeView(node)
        nodesPane.add(nodeView)
        nodeView.attach(this)

        return nodeView
    }

    private fun removeNode(node: NodeModel) {
        val view = node.view ?: return
        view.detach()
        view.removeFromParent()
    }

    fun addEdge(edge: EdgeModel): EdgesView {
        if (edge.view != null)
            return edge.view!!

        if (!edge.isInTree) {
            treeModel!!.edges.add(edge)
            return edge.view!!
        }

        for (existsEdge in edge.firstModel.edges()) {
            if (existsEdge.otherNode(edge.firstModel) == edge.secondModel && existsEdge.view != null) {
                val edgeView = existsEdge.view!!
                edgeView.attachEdge(edge)
                return edgeView
            }
        }

        val edgeView = EdgesView()
        edgeView.addToParent(controller!!, edgesPane)
        edgeView.attachEdge(edge)
        return edgeView
    }

    private fun removeEdge(edge: EdgeModel) {
        val view = edge.view ?: return
        view.detachEdge(edge)

        if (!view.hasEdges)
            view.removeFromParent()
    }

    fun attach(treeModel: TreeModel) {
        this.treeModel = treeModel
        treeModel.nodes.addListener(nodesChangeListener)
        treeModel.edges.addListener(edgesChangedListener)

        for (edge in treeModel.edges)


        for (node in treeModel.nodes)
            addNode(node)
    }

    fun detach() {
        treeModel!!.nodes.removeListener(nodesChangeListener)
        treeModel!!.edges.removeListener(edgesChangedListener)

        for (edge in treeModel!!.edges)
            removeEdge(edge)

        for (node in treeModel!!.nodes)
            removeNode(node)
    }

    companion object {
        init {
            importStylesheet("/stylesheets/scrollpane.css")
        }
    }
}

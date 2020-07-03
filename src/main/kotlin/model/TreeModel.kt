package model

import javafx.collections.SetChangeListener
import tornadofx.*

class TreeModel {
    val nodes = observableSetOf<NodeModel>().also { nodes ->
        nodes.addListener(SetChangeListener { change ->
            if (change.wasAdded()) {
                change.elementAdded.attach(this)
            }
            else {
                val node = change.elementRemoved
                while (node.edges().isNotEmpty())
                    edges.remove(node.edges().first())
                node.detach()
            }
        })
    }
    val edges = observableSetOf<EdgeModel>().also { edges ->
        edges.addListener(SetChangeListener { change ->
            if (change.wasAdded()) {
                change.elementAdded.attach(this)
            } else {
                change.elementRemoved.detach()
            }
        })
    }

    @Deprecated("Work directly with TreeModel.nodes", ReplaceWith("nodes.add(node)"))
    fun addNode(node: NodeModel) {
        nodes.add(node)
    }

    @Deprecated("Work directly with TreeModel.nodes", ReplaceWith("nodes.remove(node)"))
    fun removeNode(node: NodeModel) {
        nodes.remove(node)
    }

    @Deprecated("Work directly with TreeModel.edges", ReplaceWith("edges.add(edge)"))
    fun addEdge(edge: EdgeModel) {
        edges.add(edge)
    }

    @Deprecated("Work directly with TreeModel.edges", ReplaceWith("edges.remove(edge)"))
    fun removeEdge(edge: EdgeModel) {
        edges.remove(edge)
    }

    fun findNodeByName(name: String) = nodes.find { it.name == name }
}
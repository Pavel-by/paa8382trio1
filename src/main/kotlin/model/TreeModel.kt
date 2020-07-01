package model

import tornadofx.*

class TreeModel {
    val nodes = observableListOf<NodeModel>()
    val edges = observableListOf<EdgeModel>()

    fun addNode(node: NodeModel) {
        assert(!node.isInTree)

        node.attach(this)
        nodes.add(node)
    }

    fun removeNode(node: NodeModel) {
        assert(node.isInTree)

        for (edge in node.edges())
            removeEdge(edge)

        nodes.remove(node)
        node.detach()
    }

    fun addEdge(edge: EdgeModel) {
        edge.attach(this)
        edges.add(edge)
    }

    fun removeEdge(edge: EdgeModel) {
        edges.remove(edge)
        edge.detach()
    }

    fun findNodeByName(name: String) = nodes.find { it.name == name }
}
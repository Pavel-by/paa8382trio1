package model

import org.junit.Test

import org.junit.Assert.*

class NodeModelTest {

    @Test

    fun isInTree() {
        val node = NodeModel("name")
        val tree = TreeModel()
        node.attach(tree)
        assertEquals(node.isInTree, true)
        node.detach()
        assertEquals(node.isInTree,false)
    }

    @Test
    fun attach() {
        val node = NodeModel("name")
        val tree = TreeModel()
        node.attach(tree)
        assertEquals(node.treeModel == tree, true)
    }

    @Test
    fun detach() {
        val node = NodeModel("name")
        val tree = TreeModel()
        node.attach(tree)
        node.detach()
        assertEquals(node.treeModel == null,true)
    }

    @Test
    fun addEdge() {
        val node1 = NodeModel("node1")
        val node2 = NodeModel("node2")
        val tree = TreeModel()
        node1.attach(tree)
        val edge1 = EdgeModel(node1,node2,2.0)
        node1.addEdge(edge1)
        assertEquals(node1.edges().last() == edge1,true)
    }

    @Test
    fun removeEdge() {
        val node1 = NodeModel("node1")
        val node2 = NodeModel("node2")
        val tree = TreeModel()
        node1.attach(tree)
        val edge1 = EdgeModel(node1,node2, 2.0)
        node1.addEdge(edge1)
        node1.removeEdge(edge1)
        assertEquals(node2.edges().size == 0,true)
        val edge2 = EdgeModel(node1,node2,3.0)
        node1.addEdge(edge1)
        node1.addEdge(edge2)
        node1.removeEdge(edge2)
        assertEquals(node1.edges().last() != edge2,true)
    }
}
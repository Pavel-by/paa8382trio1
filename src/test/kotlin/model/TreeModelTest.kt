package model

import org.junit.Test

import org.junit.Assert.*

class TreeModelTest {
    @Test
    fun addNode() {
        val node = NodeModel()
        val tree = TreeModel()
        tree.addNode(node)
        assertEquals(tree.nodes.last() == node,true)
    }

    @Test
    fun removeNode() {
        val node1 = NodeModel()
        val tree = TreeModel()
        tree.addNode(node1)
        tree.removeNode(node1)
        assertEquals(tree.nodes.size == 0,true)
        val node2 = NodeModel()
        tree.addNode(node2)
        tree.addNode(node1)
        tree.removeNode(node1)
        assertEquals(tree.nodes.last() == node1,false)
    }

    @Test
    fun addEdge() {
        val node1 = NodeModel()
        val node2 = NodeModel()
        val edge = EdgeModel(node1,node2,2.0)
        val tree = TreeModel()
        node1.attach(tree)
        node2.attach(tree)
        tree.addEdge(edge)
        assertEquals(tree.edges.last() == edge,true)
    }

    @Test
    fun removeEdge() {
        val node1 = NodeModel()
        val node2 = NodeModel()
        val edge1 = EdgeModel(node1,node2,2.0)
        val edge2 = EdgeModel(node1,node2,1.0)
        val tree = TreeModel()
        node1.attach(tree)
        node2.attach(tree)
        tree.addEdge(edge1)
        tree.removeEdge(edge1)
        assertEquals(tree.edges.size == 0,true)
        tree.addEdge(edge2)
        tree.addEdge(edge1)
        tree.removeEdge(edge1)
        assertEquals(tree.edges.last() != edge1,true)
    }

    @Test
    fun findNodeByName() {
        val name = "node"
        val node = NodeModel(name)
        val tree = TreeModel()
        tree.addNode(node)
        assertEquals(tree.findNodeByName(name) == node,true)
    }
}
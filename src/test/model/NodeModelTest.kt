package model

import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

class NodeModelTest {

    @Test
    fun isInTree() {
        val node = NodeModel()
        val tree = TreeModel()
        node.attach(tree)
        assertEquals(node.isInTree,true)
        node.detach()
        assertEquals(node.isInTree,false)
    }

    @Test
    fun attach() {
    }

    @Test
    fun detach() {
    }

    @Test
    fun addEdge() {
    }

    @Test
    fun removeEdge() {
    }

    @Test
    fun edges() {
    }
}
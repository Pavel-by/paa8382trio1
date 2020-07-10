package model

import org.junit.Test

import org.junit.Assert.*

class EdgeModelTest {

    @Test
    fun runEdgeModelTest(){
        println("EdgeModel is being tested:")
        attach()
        detach()
        isInTree()
        getLength()
        getFirstModel()
        getSecondModel()
        println("-------------------------")
    }

    @Test
    fun getLength() {
        val node1 = NodeModel()
        val node2 = NodeModel()
        val len = 2.0
        val edge = EdgeModel(node1,node2,len)
        assertEquals(edge.length == len,true)
        println("getLength function works coorectly")
    }

    @Test
    fun isInTree() {
        val node1 = NodeModel()
        val node2 = NodeModel()
        val tree = TreeModel()
        val len = 2.0
        node1.attach(tree)
        node2.attach(tree)
        val edge = EdgeModel(node1,node2,len)
        edge.attach(tree)
        assertEquals(edge.isInTree,true)
        edge.detach()
        assertEquals(edge.isInTree, false)
        println("isInTree function works coorectly")
    }

    @Test
    fun attach() {
        val node1 = NodeModel()
        val node2 = NodeModel()
        val edge = EdgeModel(node1,node2,2.0)
        val tree = TreeModel()
        node1.attach(tree)
        node2.attach(tree)
        edge.attach(tree)
        assertEquals(edge.treeModel == tree,true)
        println("attach function works coorectly")
    }

    @Test
    fun detach() {
        val node1 = NodeModel()
        val node2 = NodeModel()
        val edge = EdgeModel(node1,node2,2.0)
        val tree = TreeModel()
        node1.attach(tree)
        node2.attach(tree)
        edge.attach(tree)
        edge.detach()
        assertEquals(edge.treeModel == null,true)
        println("detach function works coorectly")
    }

    @Test
    fun getFirstModel() {
        val node1 = NodeModel()
        val node2 = NodeModel()
        val edge = EdgeModel(node1,node2,2.0)
        assertEquals(edge.firstModel == node1,true)
        println("getFirstModel function works coorectly")
    }

    @Test
    fun getSecondModel() {
        val node1 = NodeModel()
        val node2 = NodeModel()
        val edge = EdgeModel(node1,node2,2.0)
        assertEquals(edge.secondModel == node2,true)
        println("getSecondModel function works coorectly")
    }
}
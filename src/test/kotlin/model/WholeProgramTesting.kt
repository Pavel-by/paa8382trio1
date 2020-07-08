package model

import org.junit.Test

import org.junit.Assert.*

class WholeProgramTesting {
    @Test
    fun run(){
        val node_model_test =  NodeModelTest()
        val edge_model_test = EdgeModelTest()
        val tree_model_test = TreeModelTest()
        val boruvka_algorithm_test = BoruvkaAlgorithmTest()
        node_model_test.runNodeModelTest()
        edge_model_test.runEdgeModelTest()
        tree_model_test.runTreeModelTest()
        boruvka_algorithm_test.process()
    }
}
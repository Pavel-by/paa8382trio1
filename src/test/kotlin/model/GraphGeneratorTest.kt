package model

import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Test
import tornadofx.onChange
import ui.controller.TreeViewController
import ui.TrioWorkspace

class GraphGeneratorTest {

    @Test
    fun getResultCode() {
    }

    @Test
    fun getErrorMessage() {
    }

    @Test
    fun hasError() {
    }

    @Test
    fun process() {
        val treeView = ui.views.TreeView().apply {
            controller = TreeViewController(this).apply {
                addEdgeSelectedProperty.onChange {}
                addNodeSelectedProperty.onChange { updateButtons() }
                currentStepProperty.onChange { updateButtons() }
                maxStepProperty.onChange { updateButtons() }
                editProperty.onChange { TrioWorkspace. }
            }
        }
//        val graph = GraphGenerator()
//        val number_of_iterations = 1000
//        var count = 0;
//        while (count < number_of_iterations){
//            val numer_of_nodes = (0..100).random()
//            val number_of_edges = (0..100).random()
//            graph.process(treeview,numer_of_nodes,number_of_edges)
//            assertEquals(treeview.treeModel.nodes.size == numer_of_nodes && treeview.treeModel.edges.size == number_of_edges,true)
//            count++
//        }
    }
}
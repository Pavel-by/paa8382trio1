package model

import junit.framework.Assert.assertEquals
import org.junit.Test
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*


class BoruvkaAlgorithmTest {

    @Test
    fun process() {
        println("Boruvka performance is being tested:")
        val workingDirectory: Path = Paths.get("").toAbsolutePath()
        val input_name:String = workingDirectory.toString() + "/src/test/kotlin/model/" +  "BoruvkaInput/"
        val output_name:String = workingDirectory.toString() + "/src/test/kotlin/model/"+ "BoruvkaOutput/"
        val dir = File(input_name)
        var input_file:File
        var output_file:File
        var tree:TreeModel
        var boruvka:BoruvkaAlgorithm
        var scanner:Scanner
        try {
            for (item in dir.listFiles()) {
                input_file = File(item.path)
                output_file  = File(output_name + "Answer" + item.path.substring(item.path.length - 6, item.path.length))
                scanner = Scanner(output_file)
                tree = input(input_file)
                boruvka = BoruvkaAlgorithm()
                boruvka.process(tree)
                assertEquals(computeTotalWeight(tree) == scanner.nextDouble(),true)
                println("test from file " + item.path + " passed")
            }
        }
        catch (e: Exception){
            print(e.message)
        }
    }

    fun computeTotalWeight(tree:TreeModel):Double{
        var total = 0.0
        for (item in tree.edges){
            if(item.step != -1)
                total += item.length
        }
        return total
    }

    fun input(file: File): TreeModel {
        val scanner = Scanner(file)
        val tree = TreeModel()
        val number_of_nodes = scanner.nextInt()
        val number_of_edges = scanner.nextInt()
        var first_node_name:Int
        var second_node_name:Int
        var len:Double
        for (x in 0..number_of_edges - 1){
            first_node_name = scanner.nextInt()
            second_node_name = scanner.nextInt()
            len = scanner.nextDouble()
            if(tree.findNodeByName(first_node_name.toString()) == null)
                    tree.addNode(NodeModel(first_node_name.toString()))
            if(tree.findNodeByName(second_node_name.toString()) == null)
                tree.addNode(NodeModel(second_node_name.toString()))
            tree.addEdge(EdgeModel(tree.findNodeByName(first_node_name.toString())!! , tree.findNodeByName(second_node_name.toString())!! ,len))
        }
        return tree
    }
}
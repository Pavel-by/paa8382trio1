package utils

import model.EdgeModel
import model.NodeModel
import tornadofx.getDouble
import tornadofx.loadJsonObject
import ui.views.TreeView
import java.io.File
import javax.json.Json
import javax.json.JsonObject
import javax.json.JsonObjectBuilder

class FileStorager {
    var error: String? = null
        private set

    fun read(view: TreeView, file: File): Boolean {
        try {
            val reader = file.reader()
            val text = reader.readText()
            reader.close()

            view.treeModel!!.nodes.clear()
            val json = loadJsonObject(text)

            for (jsonNode in json[NODES_KEY]!!.asJsonArray()) {
                jsonNode as JsonObject
                val node = NodeModel(jsonNode.getString(NODE_NAME))
                val nodeView = view.addNode(node)

                if (jsonNode.containsKey(NODE_X) && jsonNode.containsKey(NODE_Y)) {
                    nodeView.x = jsonNode.getJsonNumber(NODE_X).doubleValue()
                    nodeView.y = jsonNode.getJsonNumber(NODE_Y).doubleValue()
                }
            }

            for (jsonEdge in json[EDGES_KEY]!!.asJsonArray()) {
                jsonEdge as JsonObject
                val first = view.treeModel!!.findNodeByName(jsonEdge.getString(EDGE_FIRST))!!
                val second = view.treeModel!!.findNodeByName(jsonEdge.getString(EDGE_SECOND))!!
                val length = jsonEdge.getDouble(EDGE_LENGTH)

                view.addEdge(EdgeModel(
                    first,
                    second,
                    length
                ))
            }

            return true
        } catch (e: Exception) {
            error = "Не удалось прочитать данные из файла."
            e.printStackTrace()
            return false
        }
    }

    fun write(view: TreeView, file: File): Boolean {
        try {
            val writer = file.writer()
            val json = Json.createObjectBuilder()

            json.add(NODES_KEY, Json.createArrayBuilder().apply {
                for (node in view.treeModel!!.nodes) {
                    add(Json.createObjectBuilder().apply {
                        add(NODE_NAME, node.name)
                        add(NODE_X, node.view!!.x)
                        add(NODE_Y, node.view!!.y)
                    })
                }
            })

            json.add(EDGES_KEY, Json.createArrayBuilder().apply {
                for (edge in view.treeModel!!.edges) {
                    add(Json.createObjectBuilder().apply {
                        add(EDGE_FIRST, edge.firstModel.name)
                        add(EDGE_SECOND, edge.secondModel.name)
                        add(EDGE_LENGTH, edge.length)
                    })
                }
            })

            writer.write(json.build().toString())
            writer.close()
            return true
        } catch(e: Exception) {
            error = "Не удалось записать данные в файл"
            e.printStackTrace()
            return false
        }
    }

    companion object {
        const val NODES_KEY = "nodes"
        const val EDGES_KEY = "edges"

        const val NODE_NAME = "name"
        const val NODE_X = "x"
        const val NODE_Y = "y"

        const val EDGE_FIRST = "first"
        const val EDGE_SECOND = "second"
        const val EDGE_LENGTH = "length"
    }
}
package model

import java.util.*
import kotlin.collections.ArrayList

typealias BoruvkaComponents = ArrayList<Pair<ArrayList<NodeModel>, LinkedList<EdgeModel>>>

class BoruvkaAlgorithm {
    var graphIsConnected = false
        private set

    fun process(graph: TreeModel) {
        for (edge in graph.edges) {
            edge.step = -1
        }
        graphIsConnected = true

        // Массив компонент графа, которые должны слиться в одну
        var components = graphToBoruvkaComponents(graph)

        var stepCounter = 0
        while (components.size > 1) {
            // nextComponents отвечает за формирование components на следующую итерацию
            val nextComponents = BoruvkaComponents()
            // assignment какое множество с каким объединять
            val assignment = Array(components.size) { i -> i }


            for (i in components.indices) {
                // Если здесь в какой-то момент окажется пустой edgeList, то граф не связный
                val edgeList = components[i].second
                // Компонента, не связанная с остальным графом, не отобразится в следующем nextComponents
                // Таким образом, условие завершения while(components.size > 1) в любом случае выполнится
                if (edgeList.isEmpty()) {
                    graphIsConnected = false
                    continue
                }

                // Нахождение минимального ребра для текущей компоненты
                val minEdge = findMinEdge(edgeList)!!
                // Оно сразу же удаляется из списка
                edgeList.remove(minEdge)

                // Вершина, принадлежащая текущему множеству
                val nodeFromCurrentGroup = determineNodeOfEdgeByArray(minEdge, components[i].first)!!
                // Вершина с другого конца ребра
                val nodeFromOtherGroup = determineNodeOfEdgeByOtherNode(minEdge, nodeFromCurrentGroup)!!


                // Если никакое ребро из предыдущих множеств не ведет в текущее множество
                if (assignment[i] >= nextComponents.size) {
                    // Проврка, сольется ли текущее множество с каким-то из предыдущих за счет ребра текущей итерации
                    val (uniteFlag: Boolean, index: Int) = getGroupByNode(nextComponents, nodeFromOtherGroup)

                    // Если текущее минимальное ребро ведет в какую-то из предыдущих компонент, слияние с ним
                    if (uniteFlag) {
                        assignment[i] = index
                        nextComponents[index].first.addAll(components[i].first)
                        nextComponents[index].second.addAll(components[i].second)
                    }
                    // Если текущее не сливается ни с каким из предыдущих, добавленое новой отдельной компоненты
                    else {
                        nextComponents.add(components[i])
                        assignment[i] = nextComponents.size - 1
                    }
                }
                // Иначе текущее множество объединяется с уже существующим
                else {
                    val index = assignment[i]
                    nextComponents[index].first.addAll(components[i].first)
                    nextComponents[index].second.addAll(components[i].second)
                }

                // Переопределение assignment для того множества, в которое ведет minEdge
                if (minEdge.step >= 0) {
                    for (j in i + 1 until components.size) {
                        if (components[j].first.contains(nodeFromOtherGroup)) {
                            assignment[j] = assignment[i]
                            break
                        }
                    }
                }

                minEdge.step = stepCounter
            }

            // Из каждого множества удаляются внутренние ребра
            removeInternalEdges(nextComponents)

            // Переход к компоненте следующей итерации
            components = nextComponents
            stepCounter++
        }
        return
    }

    private fun graphToBoruvkaComponents(graph: TreeModel): BoruvkaComponents {
        val components = BoruvkaComponents()
        val nodes = graph.nodes
        for (node in nodes) {
            val nodeArray = ArrayList<NodeModel>()
            nodeArray.add(node)
            val edgeList = LinkedList<EdgeModel>(node.edges())
            components.add(Pair(nodeArray, edgeList))
        }
        return components
    }

    private fun findMinEdge(edgeList: List<EdgeModel>): EdgeModel? {
        if (edgeList.isEmpty()) return null

        var minEdge = edgeList.first()
        for (edge in edgeList) {
            if (edge.length < minEdge.length) {
                minEdge = edge
            }
        }
        return minEdge
    }

    private fun determineNodeOfEdgeByArray(edge: EdgeModel, nodeArray: ArrayList<NodeModel>): NodeModel? {
        if (nodeArray.contains(edge.firstModel)) return edge.firstModel
        if (nodeArray.contains(edge.secondModel)) return edge.secondModel
        return null
    }

    private fun determineNodeOfEdgeByOtherNode(edge: EdgeModel, nodeModel: NodeModel): NodeModel? {
        if (edge.firstModel.name == nodeModel.name) return edge.secondModel
        if (edge.secondModel.name == nodeModel.name) return edge.firstModel
        return null
    }

    private fun getGroupByNode(components: BoruvkaComponents, nodeModel: NodeModel): Pair<Boolean, Int> {
        for (i in components.indices) {
            if (components[i].first.contains(nodeModel)) {
                return Pair(true, i)
            }
        }
        return Pair(false, -1)
    }

    private fun removeInternalEdges(components: BoruvkaComponents) {
        for (component in components) {
            val vertexList = component.first
            val edgeList = component.second
            val edgesToRemove = LinkedList<EdgeModel>()
            for (edge in edgeList) {
                if (vertexList.contains(edge.firstModel) && vertexList.contains(edge.secondModel)) {
                    edgesToRemove.add(edge)
                }
            }
            edgeList.removeAll(edgesToRemove)
        }
    }
}

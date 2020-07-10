package model

import ui.views.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.abs
import kotlin.math.round


// В resultCode будет записан самый верхний из встреченных элементов enum
enum class GeneratorResult {
    NEGATIVE_NODE_COUNT,
    NEGATIVE_EDGE_COUNT,
    IMPOSSIBLE_TO_BUILD_EDGES,
    OK
}


class GraphGenerator {
    var resultCode = GeneratorResult.OK
        private set
    var errorMessage = ""
        private set


    fun hasError() = resultCode != GeneratorResult.OK

    private fun updateErrorMessage() {
        errorMessage = when(resultCode) {
            GeneratorResult.NEGATIVE_NODE_COUNT -> "Отрицательное число вершин"
            GeneratorResult.NEGATIVE_EDGE_COUNT -> "Отрицательное число ребер"
            GeneratorResult.IMPOSSIBLE_TO_BUILD_EDGES -> "Невозможно построить ребра, если вершин меньше двух"
            else -> ""
        }
    }


    fun process(treeView: TreeView, nodeCount: Int, edgeCount: Int) {
        if (nodeCount < 0) {
            resultCode = GeneratorResult.NEGATIVE_NODE_COUNT
            updateErrorMessage()
            return
        }
        if (edgeCount < 0) {
            resultCode = GeneratorResult.NEGATIVE_EDGE_COUNT
            updateErrorMessage()
            return
        }
        if (nodeCount < 2 && edgeCount > 0) {
            resultCode = GeneratorResult.IMPOSSIBLE_TO_BUILD_EDGES
            updateErrorMessage()
            return
        }
        resultCode = GeneratorResult.OK
        updateErrorMessage()

        treeView.treeModel!!.nodes.clear()

        val maxSize = if(nodeCount < 50) 1000 else 2000
        val borderSize = 40

        val nodeArray = ArrayList<NodeModel>()
        val occupiedPoints = ArrayList<Pair<Double, Double>>()
        var limitExceeded = false
        for (i in 0 until nodeCount) {
            val maxAttemptCount = 100
            var attemptCount = 0

            var point: Pair<Double, Double> = generatePoint(borderSize.toDouble(), (maxSize - borderSize).toDouble())

            if (!limitExceeded) {
                while (!checkPointAvailability(point, occupiedPoints)) {
                    attemptCount++
                    if (attemptCount == maxAttemptCount) {
                        limitExceeded = true
                        break
                    }
                    point = generatePoint(borderSize.toDouble(), (maxSize - borderSize).toDouble())
                }
            }
            occupiedPoints.add(point)

            val nodeModel = NodeModel(i.toString())
            nodeArray.add(nodeModel)
            val nodeView = treeView.addNode(nodeModel)
            nodeView.x = point.first
            nodeView.y = point.second
        }

        repeat(edgeCount) {
            val randomFirstNode = ThreadLocalRandom.current().nextInt(0, nodeArray.size)
            var randomSecondNode = ThreadLocalRandom.current().nextInt(0, nodeArray.size)
            while (randomFirstNode == randomSecondNode) {
                randomSecondNode = ThreadLocalRandom.current().nextInt(0, nodeArray.size)
            }
            // Типа округление длины до двух знаков, ок, да. Длина от 0 до 100
            val randomLength =  round(ThreadLocalRandom.current().nextDouble(0.0,100.0) * 100) / 100

            val edgeModel = EdgeModel(nodeArray[randomFirstNode], nodeArray[randomSecondNode], randomLength)
            treeView.treeModel!!.edges.add(edgeModel)
        }
    }

    private fun generatePoint(minCoord: Double, maxCoord: Double): Pair<Double, Double> {
        val x = ThreadLocalRandom.current().nextDouble(minCoord,maxCoord)
        val y = ThreadLocalRandom.current().nextDouble(minCoord,maxCoord)
        return Pair(x, y)
    }

    private fun checkPointAvailability(point: Pair<Double, Double>, array: ArrayList<Pair<Double, Double>>): Boolean {
        val minDistance = 60
        for (occupiedPoint in array) {
            if (abs(occupiedPoint.first - point.first) < minDistance &&
                    abs(occupiedPoint.second - point.second) < minDistance) {
                return false
            }
        }
        return true
    }
}
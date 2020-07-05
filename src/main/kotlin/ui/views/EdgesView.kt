package ui.views

import javafx.collections.SetChangeListener
import javafx.scene.Parent
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Line
import javafx.scene.text.Text
import model.EdgeModel
import tornadofx.*
import ui.controller.TreeViewController
import java.lang.Double.min
import kotlin.math.absoluteValue

class EdgeLength(val controller: TreeViewController, val edge: EdgeModel) : StackPane() {
    private val selectedEdgesChangeListener = SetChangeListener<EdgeModel> {
        if (it.wasAdded() && it.elementAdded == edge)
            isSelected = true

        if (it.wasRemoved() && it.elementRemoved == edge)
            isSelected = false
    }
    private val subscribeProperty = booleanProperty(false).apply {
        onChange {
            if (it) {
                if (text.textProperty().isBound)
                    text.textProperty().unbind()

                text.textProperty().bind(edge.lengthProperty.stringBinding { it.toString() })
                controller.selectedEdges.addListener(selectedEdgesChangeListener)
            } else {
                text.textProperty().unbind()
                controller.selectedEdges.removeListener(selectedEdgesChangeListener)
            }
        }
    }
    val selectedProperty = booleanProperty(false).apply {
        onChange {
            if (it) addPseudoClass("selected")
            else removePseudoClass("selected")
        }
    }
    var isSelected by selectedProperty
        private set

    val text: Text

    init {
        addClass("edge-length")

        text = text {
            addClass("edge-length-text")
        }

        setOnMouseClicked {
            controller.onEdgeClick(edge, it)
        }

        parentProperty().onChange {
            subscribeProperty.value = it != null
        }
    }
}

class EdgesView {
    val edges = hashSetOf<EdgeModel>()
    val edgeToView = hashMapOf<EdgeModel, EdgeLength>()

    val hasEdges: Boolean
        get() = edges.isNotEmpty()

    val startXProperty = doubleProperty()
    var startX by startXProperty

    val startYProperty = doubleProperty()
    var startY by startYProperty

    val endXProperty = doubleProperty()
    var endX by endXProperty

    val endYProperty = doubleProperty()
    var endY by endYProperty

    var controller: TreeViewController? = null

    val lineView = Line().apply {
        stroke = Color.rgb(183, 183, 183)

        startXProperty().bind(this@EdgesView.startXProperty)
        startYProperty().bind(this@EdgesView.startYProperty)
        endXProperty().bind(this@EdgesView.endXProperty)
        endYProperty().bind(this@EdgesView.endYProperty)
    }
    val lengthsView = HBox().apply {
        spacing = 3.0
    }

    init {
        startXProperty.onChange { validatePosition() }
        startYProperty.onChange { validatePosition() }
        endXProperty.onChange { validatePosition() }
        endYProperty.onChange { validatePosition() }
        lengthsView.layoutBoundsProperty().onChange { validatePosition() }
    }

    private fun validatePosition() {
        val centerX = (startX - endX).absoluteValue / 2 + min(startX, endX)
        val centerY = (startY - endY).absoluteValue / 2 + min(startY, endY)

        lengthsView.layoutX = centerX - lengthsView.width / 2
        lengthsView.layoutY = centerY - lengthsView.height / 2
    }

    fun addToParent(controller: TreeViewController, parent: Parent) {
        this.controller = controller
        parent.add(lineView)
        parent.add(lengthsView)
    }

    fun removeFromParent() {
        controller = null
        lineView.removeFromParent()
        lengthsView.removeFromParent()
    }

    fun attachEdge(edge: EdgeModel) {
        if (edges.isEmpty()) {
            startXProperty.bind(edge.firstModel.view!!.xProperty)
            startYProperty.bind(edge.firstModel.view!!.yProperty)
            endXProperty.bind(edge.secondModel.view!!.xProperty)
            endYProperty.bind(edge.secondModel.view!!.yProperty)
        } else {
            assert(edge.firstModel == edges.first().firstModel)
            assert(edge.secondModel == edges.first().secondModel)
        }

        edge.view = this
        edges.add(edge)
        edgeToView[edge] = EdgeLength(controller!!, edge)
        lengthsView.add(edgeToView[edge]!!)
    }

    fun detachEdge(edge: EdgeModel) {
        edges.remove(edge)
        edgeToView[edge]?.removeFromParent()
        edgeToView.remove(edge)

        if (edges.isEmpty()) {
            startXProperty.unbind()
            startYProperty.unbind()
            endXProperty.unbind()
            endYProperty.unbind()
        }
    }

    companion object {
        init {
            importStylesheet("/stylesheets/edge.css")
        }
    }
}
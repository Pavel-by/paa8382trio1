package ui.views

import javafx.collections.SetChangeListener
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.shape.StrokeType
import javafx.scene.text.Font
import javafx.scene.text.Text
import model.NodeModel
import tornadofx.*

class NodeView(val node: NodeModel) : View() {
    private var isDragging = false
    private var dragLastX: Double = 0.0
    private var dragLastY: Double = 0.0
    private val selectedChangeListener = SetChangeListener<NodeModel> {
        isSelected = it.set.contains(node)
    }

    val xProperty = doubleProperty(0.0)
    var x: Double by xProperty

    val yProperty = doubleProperty(0.0)
    var y: Double by yProperty

    private val treeViewProperty = objectProperty<TreeView?>()
    var treeView: TreeView? by treeViewProperty
        private set

    private val selectedProperty = booleanProperty(false)
    var isSelected: Boolean by selectedProperty
        private set

    lateinit var textView: Text
    lateinit var rect: Rectangle

    override val root = stackpane {
        setOnMousePressed {
            dragLastX = it.sceneX
            dragLastY = it.sceneY
        }

        setOnMouseDragged {
            if (it.isDragDetect)
                isDragging = true

            x += it.sceneX - dragLastX
            y += it.sceneY - dragLastY
            dragLastX = it.sceneX
            dragLastY = it.sceneY
            it.consume()
        }

        setOnMouseClicked {
            if (isDragging) {
                isDragging = false
                it.consume()
            }
            else {
                treeView!!.controller!!.onNodeClick(node, it)
            }
        }

        alignment = Pos.CENTER

        layoutXProperty().bind(xProperty.subtract(widthProperty().divide(2)))
        layoutYProperty().bind(yProperty.subtract(heightProperty().divide(2)))

        rect = rectangle {
            fill = Color.YELLOW
            arcHeight = 100.0
            arcWidth = 100.0
            stroke = Color.BLACK
            strokeProperty().bind(selectedProperty.objectBinding {
                if (it!!) Color.BLACK
                else Color.rgb(183, 183, 183)
            })
            strokeWidth = 2.0
            strokeType = StrokeType.INSIDE
        }

        textView = text {
            text = "Text"
            font = Font.font(20.0)
        }.also { t ->
            rect.widthProperty().bind(t.layoutBoundsProperty().doubleBinding { it!!.width + 30 })
            rect.heightProperty().bind(t.layoutBoundsProperty().doubleBinding { it!!.height + 30 })
        }
    }

    fun attach(treeView: TreeView) {
        this.treeView = treeView
        node.view = this
        textView.text = "Hello world!"
        textView.textProperty().bind(node.nameProperty)
        treeView.controller!!.selectedNodes.addListener(selectedChangeListener)
    }

    fun detach() {
        node.view = null
        textView.textProperty().unbind()
        treeView!!.controller!!.selectedNodes.removeListener(selectedChangeListener)
        treeView = null
    }
}
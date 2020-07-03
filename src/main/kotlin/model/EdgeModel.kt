package model

import javafx.beans.property.ReadOnlyObjectProperty
import tornadofx.*
import ui.views.EdgesView

class EdgeModel(
    val firstModel: NodeModel,
    val secondModel: NodeModel,
    defaultLength: Double = 0.0
) {
    private val treeModelProperty = objectProperty<TreeModel?>()
    var treeModel: TreeModel? by treeModelProperty
        private set

    val lengthProperty = doubleProperty(defaultLength)
    var length: Double by lengthProperty

    val inTreeProperty = treeModelProperty.booleanBinding { it != null }
    val isInTree: Boolean by inTreeProperty

    val stepProperty = intProperty(-1)
    var step: Int by stepProperty

    val viewProperty = objectProperty<EdgesView>()
    var view by viewProperty

    fun otherNode(model: NodeModel) = if (model == firstModel) secondModel else firstModel

    fun treeModelProperty(): ReadOnlyObjectProperty<TreeModel?> = treeModelProperty

    fun attach(treeModel: TreeModel) {
        assert(!isInTree)
        this.treeModel = treeModel
        firstModel.addEdge(this)
        secondModel.addEdge(this)
    }

    fun detach() {
        assert(isInTree)
        firstModel.removeEdge(this)
        secondModel.removeEdge(this)
        treeModel = null
    }

    override fun toString(): String {
        return "EdgeModel(length=$length, first=$firstModel, second=$secondModel)"
    }
}
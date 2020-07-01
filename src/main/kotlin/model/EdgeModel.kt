package model

import javafx.beans.property.ReadOnlyBooleanProperty
import javafx.beans.property.ReadOnlyObjectProperty
import tornadofx.*

class EdgeModel(
    private val firstName: String,
    private val secondName: String,
    defaultLength: Double = 0.0
) {
    private val firstModelProperty = objectProperty<NodeModel>()
    var firstModel: NodeModel by firstModelProperty
        private set

    private val secondModelProperty = objectProperty<NodeModel>()
    var secondModel: NodeModel by secondModelProperty
        private set

    private val treeModelProperty = objectProperty<TreeModel?>()
    var treeModel: TreeModel? by treeModelProperty
        private set

    val lengthProperty = doubleProperty(defaultLength)
    var length: Double by lengthProperty

    val inTreeProperty = treeModelProperty.booleanBinding { it != null }
    val isInTree: Boolean by inTreeProperty

    fun firstModelProperty(): ReadOnlyObjectProperty<NodeModel> = firstModelProperty

    fun secondModelProperty(): ReadOnlyObjectProperty<NodeModel> = secondModelProperty

    fun treeModelProperty(): ReadOnlyObjectProperty<TreeModel?> = treeModelProperty

    fun attach(treeModel: TreeModel) {
        assert(!isInTree)
        firstModel = treeModel.findNodeByName(firstName)!!
        secondModel = treeModel.findNodeByName(secondName)!!
        firstModel.add(this)
        secondModel.add(this)
        this.treeModel = treeModel
    }

    fun detach() {
        assert(isInTree)
        firstModel.remove(this)
        secondModel.remove(this)
        treeModel = null
    }

    override fun toString(): String {
        return "EdgeModel(length=$length, first=$firstModel, second=$secondModel)"
    }
}
package model

import javafx.beans.property.ReadOnlyObjectProperty
import tornadofx.*

class NodeModel(
    defaultName: String = ""
) {
    private val edges = arrayListOf<EdgeModel>()

    val nameProperty = stringProperty(defaultName)
    var name: String by nameProperty

    private val treeModelProperty = objectProperty<TreeModel?>()
    var treeModel: TreeModel? by treeModelProperty
        private set

    val inTreeProperty = treeModelProperty.booleanBinding { it != null }
    val isInTree: Boolean by inTreeProperty

    fun treeModelProperty(): ReadOnlyObjectProperty<TreeModel?> = treeModelProperty

    fun attach(treeModel: TreeModel) {
        assert(!isInTree)
        this.treeModel = treeModel
    }

    fun detach() {
        assert(isInTree)
        assert(edges.isEmpty())
        this.treeModel = null
    }

    fun add(edge: EdgeModel) {
        assert(isInTree)
        edges.add(edge)
    }

    fun remove(edge: EdgeModel) {
        assert(isInTree)
        edges.remove(edge)
    }

    fun edges(): List<EdgeModel> = edges

    override fun toString(): String {
        return "NodeModel(name='$name')"
    }
}

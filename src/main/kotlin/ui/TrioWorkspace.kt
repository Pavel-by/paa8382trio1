package ui

import model.TreeModel
import tornadofx.*
import ui.controller.TreeViewController
import ui.views.TreeView

class TrioWorkspace : Workspace("Tree") {
    private val treeModel = TreeModel()
    private val treeView = TreeView().apply {
        controller = TreeViewController(this)
    }

    override val closeable = booleanProperty(false)
    override val complete = booleanProperty(false)
    override val creatable = booleanProperty(false)
    override val deletable = booleanProperty(false)
    override val refreshable = booleanProperty(false)
    override val savable = booleanProperty(false)

    override fun onDock() {
        super.onDock()
        header.replaceChildren()
        header.addClass("toolbar")
        header.add(headingContainer)

        header.items.add(MenuButton().apply {
            text = "Добавить вершину"
            parentProperty().onChange {
                if (it != null) {
                    activeProperty.bind(treeView.controller!!.addNodeSelectedProperty)
                } else {
                    activeProperty.unbind()
                }
            }
            setOnAction {
                treeView.controller!!.onAddNodeButtonClick()
            }
        })

        header.items.add(MenuButton().apply {
            text = "Добавить ребро"
            activeProperty.bind(treeView.controller!!.addEdgeSelectedProperty)

            setOnAction {
                treeView.controller!!.onAddEdgeButtonClick()
            }
        })

        treeView.attach(treeModel)
        dock(treeView)

        assert(!creatable.value)
    }

    companion object {
        init {
            importStylesheet("/stylesheets/toolbar.css")
        }
    }
}
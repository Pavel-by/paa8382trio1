package ui.controller

import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import model.EdgeModel
import model.NodeModel
import tornadofx.*

abstract class ControllerState {
    private val stateAttachedProperty = booleanProperty(false)
    var isStateAttached: Boolean by stateAttachedProperty
        private set

    lateinit var controller: TreeViewController
        private set

    fun attach(controller: TreeViewController) {
        this.controller = controller
        isStateAttached = true
        onStart()
    }

    fun detach() {
        onStop()
        isStateAttached = false
    }

    protected open fun onStart() {}
    protected open fun onStop() {}

    abstract fun onNodeClick(node: NodeModel, event: MouseEvent)
    abstract fun onEdgeClick(edge: EdgeModel, event: MouseEvent)
    abstract fun onFieldClick(event: MouseEvent)

    abstract fun onAddNodeButtonClick()
    abstract fun onAddEdgeButtonClick()
    abstract fun onEditButtonClick()
    abstract fun onForwardButtonClick()
    abstract fun onForceForwardButtonClick()
    abstract fun onBackButtonClick()
    abstract fun onForceBackButtonClick()

    abstract fun onRootKeyPressed(event: KeyEvent)
}
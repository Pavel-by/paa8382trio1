package ui

import model.TreeModel
import tornadofx.*
import ui.controller.TreeViewController
import ui.views.TreeView

class TrioWorkspace : Workspace("Tree") {
    private val treeModel = TreeModel()
    private val treeView = TreeView().apply {
        controller = TreeViewController(this).apply {
            addEdgeSelectedProperty.onChange { updateButtons() }
            addNodeSelectedProperty.onChange { updateButtons() }
            currentStepProperty.onChange { updateButtons() }
            maxStepProperty.onChange { updateButtons() }
            editProperty.onChange { updateButtons() }
        }
    }

    private var editButton = MenuButton().apply {
        text = "Редактирование"

        setOnAction {
            treeView.controller!!.onEditButtonClick()
        }
    }
    private var addNodeButton = MenuButton().apply {
        text = "Добавить вершину"

        setOnAction {
            treeView.controller!!.onAddNodeButtonClick()
        }
    }
    private var addEdgeButton = MenuButton().apply {
        text = "Добавить ребро"

        setOnAction {
            treeView.controller!!.onAddEdgeButtonClick()
        }
    }
    private val forceForwardButton = MenuButton().apply {
        text = ">>"

        setOnAction {
            treeView.controller!!.onForceForwardButtonClick()
        }
    }
    private val forceBackButton = MenuButton().apply {
        text = "<<"

        setOnAction {
            treeView.controller!!.onForceBackButtonClick()
        }
    }
    private val generateGraphButton = MenuButton().apply {
        text = "Сгенерировать граф"

        setOnAction {
            treeView.controller!!.onGenerateGraphButtonClick()
        }
    }

    private fun updateButtons() {
        if (treeView.controller!!.isEdit) {
            backButton.isDisable = true
            forwardButton.isDisable = true
            forceForwardButton.isDisable = true
            forceBackButton.isDisable = true
            addEdgeButton.isDisable = false
            addNodeButton.isDisable = false
            generateGraphButton.isDisable = false
            editButton.addPseudoClass("selected")

            if (treeView.controller!!.isAddNodeSelected) {
                addNodeButton.addPseudoClass("selected")
            } else {
                addNodeButton.removePseudoClass("selected")
            }

            if (treeView.controller!!.isAddEdgeSelected) {
                addEdgeButton.addPseudoClass("selected")
            } else {
                addEdgeButton.removePseudoClass("selected")
            }
        } else {
            backButton.isDisable = treeView.controller!!.currentStep < 0
            forceBackButton.isDisable = treeView.controller!!.currentStep < 0
            forwardButton.isDisable = treeView.controller!!.currentStep >= treeView.controller!!.maxStep
            forceForwardButton.isDisable = treeView.controller!!.currentStep >= treeView.controller!!.maxStep
            addNodeButton.removePseudoClass("selected")
            addEdgeButton.removePseudoClass("selected")
            addNodeButton.isDisable = true
            addEdgeButton.isDisable = true
            generateGraphButton.isDisable = true
            editButton.removePseudoClass("selected")
        }
    }

    override fun onDock() {
        super.onDock()
        header.replaceChildren()

        backButton.disableProperty().unbind()
        forwardButton.disableProperty().unbind()

        forwardButton.action {
            treeView.controller!!.onForwardButtonClick()
        }
        backButton.action {
            treeView.controller!!.onBackButtonClick()
        }

        header.items.add(forceBackButton)
        header.items.add(backButton)
        header.items.add(forwardButton)
        header.items.add(forceForwardButton)

        header.addClass("toolbar")
        header.items.add(0, headingContainer)
        header.items.add(editButton)
        header.items.add(addNodeButton)
        header.items.add(addEdgeButton)
        header.spacer {  }
        header.items.add(generateGraphButton)


        treeView.attach(treeModel)
        dock(treeView)

        root.setOnKeyPressed {
            treeView.controller!!.onRootKeyPressed(it)
        }

        updateButtons()
    }

    companion object {
        init {
            importStylesheet("/stylesheets/toolbar.css")
        }
    }
}
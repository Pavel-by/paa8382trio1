package ui

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.TextField
import javafx.scene.input.KeyCode
import tornadofx.*

class SimpleEditor(title: String = "Редактирование вершины") : Fragment(title) {
    val okTextProperty = stringProperty("ОК")
    var okText: String by okTextProperty

    val cancelTextProperty = stringProperty("Отмена")
    var cancelText: String by cancelTextProperty

    val errorTextProperty = stringProperty("")
    var errorText: String by errorTextProperty

    var text: String = ""
        set(value) {
            field = value
            textField?.text = value
        }
        get() = textField?.text ?: ""

    var onOk: (() -> Unit)? = null
    var onCancel: (() -> Unit)? = null

    var textField: TextField? = null

    override val root = vbox {
        alignment = Pos.CENTER

        padding = Insets(20.0, 20.0, 40.0, 20.0)
        spacing = 15.0

        label {
            addClass("editor-title")
            textProperty().bind(this@SimpleEditor.titleProperty)
        }

        textfield {
            text = this@SimpleEditor.text
            textField = this

            setOnKeyReleased {
                if (it.code == KeyCode.ENTER)
                    onOk?.invoke()
            }
        }

        hbox {
            spacing = 15.0

            button {
                textProperty().bind(okTextProperty)
                setOnAction { onOk?.invoke() }
            }

            button {
                textProperty().bind(cancelTextProperty)
                setOnAction { onCancel?.invoke() }
            }
        }

        text {
            wrappingWidthProperty().bind(this@vbox.widthProperty().doubleBinding { it!!.toDouble() - 40.0 })
            textProperty().bind(errorTextProperty)
        }
    }

    companion object {
        init {
            importStylesheet("/stylesheets/editor.css")
        }
    }
}
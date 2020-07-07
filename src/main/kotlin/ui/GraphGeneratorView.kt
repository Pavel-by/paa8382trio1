package ui

import javafx.scene.control.TextField
import tornadofx.*

class GraphGeneratorView : Fragment("Генерация графа") {
    var action: (() -> Unit)? = null

    lateinit var nodesField: TextField
    lateinit var edgesField: TextField

    override val root = vbox {
        spacing = 10.0
        paddingAll = 20.0

        hbox {
            spacing = 10.0

            label {
                text = "Количество вершин"
            }

            textfield {
                nodesField = this
                text = "0"
            }
        }

        hbox {
            spacing = 10.0

            label {
                text = "Количество ребер"
            }

            textfield {
                edgesField = this
                text = "0"
            }
        }

        button {
            text = "Сгенерировать"

            action {
                action?.invoke()
            }
        }
    }
}
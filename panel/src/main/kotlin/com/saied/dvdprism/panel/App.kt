import react.dom.p
import react.dom.render
import kotlin.browser.document
import kotlin.browser.window

fun main(args: Array<String>) {
    window.alert("test")
    window.onload = {
        val root = document.getElementById("root")
        render(root) {
            p { +"saied" }
        }
    }
}

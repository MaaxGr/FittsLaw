import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.HBox
import javafx.stage.Stage
import javafx.stage.StageStyle

fun main(args: Array<String>) {
    Application.launch(Main::class.java, *args)
}

class Main : Application() {

    companion object {
        const val TITLE = "Fitts Law"
    }

    override fun start(primaryStage: Stage) {
        primaryStage.scene = FittsLawScene().scene
        primaryStage.title = TITLE
        primaryStage.show()
    }

}
import javafx.application.Application
import javafx.stage.Stage

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
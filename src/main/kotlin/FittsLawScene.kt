import javafx.scene.Scene
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane

class FittsLawScene {
    val scene: Scene

    private lateinit var image: ImageView

    //game info
    private var inGame = false
    private var startTime: Long = 0
    private var endTime: Long = 0
    private var startPosition = Pair(0.0, 0.0)
    private var targetPosition = Pair(0.0, 0.0)

    companion object {
        const val SCENE_WIDTH = 1200.0
        const val SCENE_HEIGHT = 675.0
        const val BUG_SIZE = 48.0
    }

    init {
        //create layout
        val layout = Pane()

        //add bug image
        prepareBugImage(layout)

        //create scene
        scene = Scene(layout, SCENE_WIDTH, SCENE_HEIGHT)
    }

    private fun resetBugPosition() {
        image.x = SCENE_WIDTH / 2
        image.y = SCENE_HEIGHT / 2
    }

    private fun prepareBugImage(layout: Pane) {
        //create image
        image = ImageView("bug.png")

        //set size
        image.fitHeight = BUG_SIZE
        image.fitWidth = BUG_SIZE

        //set position
        resetBugPosition()

        //set handler
        image.setOnMouseEntered {
            bugHovered()
        }

        //add to layout
        layout.children.add(image)
    }

    private fun bugHovered() {
        if (!inGame) {
            //start game
            inGame = true
            startTime = System.currentTimeMillis()
            startPosition = Pair(image.x, image.y)

            //calculate new positions
            val newX = (0..scene.width.toInt()).random().toDouble()
            val newY = (0..scene.height.toInt()).random().toDouble()
            targetPosition = Pair(newX, newY)

            //move bug
            image.x = newX
            image.y = newY
        } else {
            //set end time
            endTime = System.currentTimeMillis()

            //info into console
            println("Game ended in ${(endTime - startTime)} millis")

            //end game
            inGame = false

            //reset bug position
            resetBugPosition()
        }

    }

}
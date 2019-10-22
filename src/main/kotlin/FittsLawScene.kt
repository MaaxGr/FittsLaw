import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import kotlin.math.abs
import kotlin.math.sqrt

class FittsLawScene {
    val scene: Scene

    private lateinit var roundLabel: Label

    //game info
    private val logger = FittsLogger()
    private var roundId: Int = 1
    private var startTime: Long = 0
    private var endTime: Long = 0
    private var lastDelay: Long = 0
    private var startCoordinate = Coordinate(0.0, 0.0)
    private var targetCoordinate = Coordinate(0.0, 0.0)

    private var midX: Double = 0.0
    private var midY: Double = 0.0

    private lateinit var monsterSize: Pair<Double, Double>

    companion object {
        const val SCENE_WIDTH = 1024.0
        const val SCENE_HEIGHT = 768.0
        const val CIRCLE_SIZE = 50.0
        const val MID_X = SCENE_WIDTH / 2
        const val MID_Y = SCENE_HEIGHT / 2
        const val BORDER = 20
    }

    init {
        //create layout
        val layout = Pane()

        prepareMonsterSize()

        addRoundLabel(layout)

        spawnGreenCycleImage(layout)


        //create scene
        scene = Scene(layout, SCENE_WIDTH, SCENE_HEIGHT)
    }

    private fun addRoundLabel(layout: Pane) {
        roundLabel = Label("")

        with(roundLabel) {
            id = "round_label"
            layoutX = 5.0
            layoutY = 5.0
        }
        layout.children.add(roundLabel)
    }

    private fun updateRoundLabelRound() {
        roundLabel.text = "Runde: $roundId/140"
    }

    private fun prepareMonsterSize() {
        val img = Image("monster.png")
        val width = img.width
        val height = img.width

        monsterSize = Pair(width, height)
    }

    private fun spawnGreenCycleImage(layout: Pane) {
        updateRoundLabelRound()

        val image = ImageView("circle.png")
        image.fitHeight = CIRCLE_SIZE
        image.fitWidth = CIRCLE_SIZE

        image.x = MID_X
        image.y = MID_Y

        image.setOnMouseClicked {
            lastDelay = getRandomDelay().toLong()
            Thread.sleep(lastDelay)

            startTime = System.currentTimeMillis()
            startCoordinate = Coordinate(image.x, image.y)

            //calculate new positions
            val newX = (BORDER..scene.width.toInt() - BORDER).random().toDouble()
            val newY = (BORDER..scene.height.toInt() - BORDER).random().toDouble()
            targetCoordinate = Coordinate(newX, newY)

            //move bug
            layout.children.remove(image)
            spawnMonsterImage(layout, newX, newY)
        }

        layout.children.add(image)
    }

    private fun spawnMonsterImage(layout: Pane, xPosition: Double, yPosition: Double) {

        //add monster to layout
        val image = ImageView("monster.png")
        val scaleFactor = getRandomScaleFactor()
        image.fitWidth = monsterSize.first * scaleFactor
        image.fitHeight = monsterSize.second * scaleFactor
        image.x = xPosition
        image.y = yPosition
        layout.children.add(image)

        //handle monster click
        image.setOnMouseClicked {
            //set end time
            endTime = System.currentTimeMillis()

            //log infos
            val millis = endTime - startTime
            val xDiff = abs(targetCoordinate.x - startCoordinate.x)
            val yDiff = abs(targetCoordinate.y - startCoordinate.y)
            val totalDiff = sqrt(xDiff * xDiff + yDiff * yDiff)

            logger.log(
                    FittsLogger.Message(
                        roundId = roundId,
                        millis = millis,
                        scaleFactor = scaleFactor,
                        diff = totalDiff,
                        delay = lastDelay
                    )
            )

            //end game
            roundId++

            layout.children.remove(image)

            spawnGreenCycleImage(layout)
        }
    }

    private fun getRandomScaleFactor(): Double {
        return listOf(0.1, 0.2, 0.3, 0.4, 0.6, 0.8, 1.0).random()
    }

    private fun getRandomDelay(): Int {
        return 500 * (1..7).random()
    }

    private data class Coordinate(val x: Double, val y: Double)

}
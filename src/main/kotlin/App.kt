import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.stage.Stage
import units.*
import javax.swing.JOptionPane

class App : Application() {

    inner class TestUnitTimer : AnimationTimer() {

        private var currentIndex = 0
        private var dataUtil = DataUtil(JOptionPane.showInputDialog("User name?"))

        private var lastTime: Long = 0

        private var fpsAvg = 0.0
        private var fpsCounter = 0
        private var lastFpsAvg = 0.0

        override fun handle(now: Long) {
            fpsCounter++
            val elapsedTime = System.currentTimeMillis() - lastTime
            lastTime = System.currentTimeMillis()

            val currentFps = (1.0 / (elapsedTime.toDouble() / 1000.0))
            fpsAvg += currentFps

            if (fpsCounter == 10) {
                lastFpsAvg = fpsAvg / (fpsCounter.toDouble())
                fpsCounter = 0
                fpsAvg = 0.0
            }

            val gc = canvas.graphicsContext2D

            gc.fill = Color.WHITE
            gc.fillRect(0.0, 0.0, WIDTH.toDouble(), HEIGHT.toDouble())
            tests[currentIndex].update(gc)


            gc.fill = Color.BLACK
            gc.font = Font.font(10.0)

            gc.fillText("Time: " + tests[currentIndex].getRuntime(), 10.0, 10.0)
            gc.fillText("Name: " + tests[currentIndex].getName(), 10.0, 30.0)
            gc.fillText("Desc: " + tests[currentIndex].getDesc(), 10.0, 50.0)
            gc.fillText("FPS: " + lastFpsAvg.toInt(), 10.0, 70.0)


            if (tests[currentIndex].isFinished()) {
                tests[currentIndex].saveResults(dataUtil)
                currentIndex++
                if (currentIndex >= tests.size) {
                    stop()
                    Platform.exit()
                    return
                }
            }
        }

        fun onMouseEvent(event: MouseEvent) {
            if (currentIndex < tests.size) {
                tests[currentIndex].onClick(event.x, event.y)
            }
        }

        fun onMouseMove(event: MouseEvent) {
            if (currentIndex < tests.size) {
                tests[currentIndex].onMove(event.x, event.y)
            }
        }

        fun onKey(event: KeyEvent) {
            if (currentIndex < tests.size) {
                tests[currentIndex].onKey(event.code)
            }
        }

    }

    private lateinit var canvas: Canvas
    private lateinit var timer: TestUnitTimer

    val tests: MutableList<TestUnit> = mutableListOf()

    override fun start(stage: Stage) {
        val root = Pane()
        canvas = Canvas(WIDTH.toDouble(), HEIGHT.toDouble())

        timer = TestUnitTimer()

        root.children.add(canvas)

        canvas.setOnMouseClicked {
            timer.onMouseEvent(it)
        }

        canvas.setOnMouseMoved {
            timer.onMouseMove(it)
        }

        val scene = Scene(root, WIDTH.toDouble(), HEIGHT.toDouble())

        scene.setOnKeyPressed {
            timer.onKey(it)
        }

        stage.scene = scene
        stage.show()
        stage.isResizable = false
        addTests()
        runTests()
    }

    companion object {
        const val WIDTH: Int = 1280
        const val HEIGHT: Int = 720
    }

    fun runTests() {
        timer.stop()
        timer.start()
    }

    fun addTests() {
        addTest(InfoUnit("Welcome to HAA (Human Ability Analytics)!\nStay safe and enjoy the flight."))
        addTest(InfoUnit("Next: Interval test.\nTry to measure the light interval."))
        addTest(InfoUnit("Next: Distance test.\nTry to measure the distance."))
        addTest(InfoUnit("Next: Reaction test.\nPress any key if you see the rectangle."))
        addTest(ReactionUnit(4, ReactionUnit.ReactionType.ONLY_BLACK))
        addTest(ReactionUnit(4, ReactionUnit.ReactionType.ONLY_RED))
        addTest(ReactionUnit(4, ReactionUnit.ReactionType.ONLY_GREY))
        addTest(ReactionUnit(4, ReactionUnit.ReactionType.ONLY_BLUE))
        addTest(ReactionUnit(4, ReactionUnit.ReactionType.ONLY_GREEN))
        addTest(InfoUnit("Next: Extend Countdown\nPress any key if you think the countdown ends."))
        addTest(CountdownUnit(5))
        addTest(InfoUnit("Next: Remember\nRemember the sequence and type it when it disappears."))
        addTest(RememberUnit(2, RememberUnit.RememberUnitType.NUMBERS_ONLY))
        addTest(RememberUnit(2, RememberUnit.RememberUnitType.TEXT_ONLY))
        addTest(RememberUnit(2, RememberUnit.RememberUnitType.TEXT_AND_NUMBERS))
        addTest(InfoUnit("Next: Angle\nTry to draw the angle and click if you think you are correct."))
        addTest(AngleUnit(5))
    }

    fun addTest(test: TestUnit) {
        tests.add(test.apply {
            width = WIDTH
            height = HEIGHT
            startTime = System.currentTimeMillis()
        })
    }

}

fun main() {
    Application.launch(App::class.java)
}

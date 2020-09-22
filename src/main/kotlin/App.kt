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
import units.AngleUnit
import units.ReactionUnit
import units.RememberUnit

class App : Application() {

    inner class TestUnitTimer : AnimationTimer() {

        private var currentIndex = 0
        private var dataUtil = DataUtil()

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

            if (fpsCounter == 5) {
                lastFpsAvg = fpsAvg / (fpsCounter.toDouble() + 1.0)
                fpsCounter = 0
                fpsAvg = 0.0
            }

            val gc = canvas.graphicsContext2D

            gc.fill = Color.WHITE
            gc.fillRect(0.0, 0.0, WIDTH.toDouble(), HEIGHT.toDouble())
            tests[currentIndex].update(gc)


            gc.fill = Color.BLACK
            gc.font = Font.font(10.0)

            gc.fillText("Time: " + tests[currentIndex].getRuntime() / 1000.0 + "s", 10.0, 10.0)
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
        addTest(RememberUnit(5, RememberUnit.RememberUnitType.NUMBERS_ONLY))
        addTest(RememberUnit(5, RememberUnit.RememberUnitType.TEXT_ONLY))
        addTest(RememberUnit(5, RememberUnit.RememberUnitType.TEXT_AND_NUMBERS))
        addTest(ReactionUnit(3, ReactionUnit.ReactionType.ONLY_BLACK))
        addTest(ReactionUnit(3, ReactionUnit.ReactionType.ONLY_GREY))
        addTest(ReactionUnit(3, ReactionUnit.ReactionType.ONLY_RED))
        addTest(ReactionUnit(3, ReactionUnit.ReactionType.ONLY_GREEN))
        addTest(ReactionUnit(3, ReactionUnit.ReactionType.ONLY_BLUE))
        addTest(AngleUnit(3))
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

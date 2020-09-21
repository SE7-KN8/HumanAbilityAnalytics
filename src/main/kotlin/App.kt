import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.Pane
import javafx.stage.Stage

class App : Application() {

    inner class TestUnitTimer : AnimationTimer() {

        private var currentIndex = 0

        override fun handle(now: Long) {
            if (currentIndex >= tests.size) {
                stop()
                return
            }
            tests[currentIndex].update(canvas.graphicsContext2D)
            if (tests[currentIndex].isFinished()) {
                currentIndex++
            }
        }

    }

    private lateinit var canvas: Canvas
    private lateinit var timer: AnimationTimer

    val tests: List<TestUnit> = mutableListOf()

    override fun start(stage: Stage) {
        val root = Pane()
        canvas = Canvas(WIDTH.toDouble(), HEIGHT.toDouble())

        timer = TestUnitTimer()

        root.children.add(canvas)

        val scene = Scene(root, WIDTH.toDouble(), HEIGHT.toDouble())
        stage.scene = scene
        stage.show()

    }

    companion object {
        const val WIDTH: Int = 1280
        const val HEIGHT: Int = 720
    }

    fun runTests() {
        timer.stop()
        timer.start()
    }

}


fun main() {
    Application.launch(App::class.java)
}

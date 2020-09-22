import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.KeyCode

abstract class TestUnit {

    var startTime: Long = 0
    var width = 0
    var height = 0

    abstract fun update(gc: GraphicsContext)

    open fun onClick(x: Double, y: Double) {

    }

    open fun onKey(e: KeyCode) {

    }

    open fun saveResults(dataUtil: DataUtil) {

    }

    fun getRuntime() = System.currentTimeMillis() - startTime

    abstract fun isFinished(): Boolean

    abstract fun getName(): String

    abstract fun getDesc(): String

    open fun onMove(x: Double, y: Double) {

    }


}

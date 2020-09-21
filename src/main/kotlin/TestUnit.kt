import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.KeyCode

abstract class TestUnit {

    abstract fun update(gc: GraphicsContext)

    fun onClick(x: Double, y: Double) {

    }

    fun onKey(e: KeyCode) {

    }

    fun saveResults(dataUtil: DataUtil) {

    }

    abstract fun isFinished(): Boolean


}

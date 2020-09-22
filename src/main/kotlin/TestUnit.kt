import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.KeyCode

abstract class TestUnit {

    var startTime: Long = 0
    var width = 0
    var height = 0

    val data = ArrayList<Int>()

    abstract fun update(gc: GraphicsContext)

    open fun onClick(x: Double, y: Double) {

    }

    open fun onKey(e: KeyCode) {

    }

    fun saveResults(dataUtil: DataUtil) {
        dataUtil.saveTestResult(getName(), data)
    }

    fun getDataAvg(): Double {
        var value: Long = 0
        data.forEach { value += it }
        return value.toDouble() / data.size.toDouble()
    }

    fun getRuntime() = System.currentTimeMillis() - startTime

    abstract fun isFinished(): Boolean

    abstract fun getName(): String

    abstract fun getDesc(): String

    open fun onMove(x: Double, y: Double) {

    }


}

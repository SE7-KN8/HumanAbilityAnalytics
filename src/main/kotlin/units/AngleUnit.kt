package units

import TestUnit
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import kotlin.math.pow
import kotlin.math.sqrt

class AngleUnit : TestUnit() {

    var lastX: Double = 0.0
    var lastY: Double = 0.0

    override fun update(gc: GraphicsContext) {
        gc.fill = Color.BLACK
        gc.stroke = Color.BLACK
        gc.lineWidth = 5.0
        val x1 = width / 2.0
        val y1 = height / 2.0
        gc.strokeLine(x1, y1, lastX, lastY)
        gc.fillOval(x1 - 10, y1- 10, 20.0, 20.0)

        val x = x1 - lastX
        val y = y1 - lastY

        val length = sqrt(x.pow(2.0) + y.pow(2.0))
        gc.strokeLine(x1, y1, x1 + length, y1)

        val vec1x = x
        val vec1y = y

        val vec2x = length
        val vec2y = 0

        val dotp = vec1x * vec2x + vec1y * vec2y

        val angle = 180.0 - Math.toDegrees(Math.acos(dotp / (length * length)))

        gc.fillText("Vec1: ${vec1x.toInt()} ${vec1y.toInt()}", 100.0, 80.0)
        gc.fillText("Vec2: ${vec2x.toInt()} ${vec2y.toInt()}", 100.0, 90.0)
        gc.fillText("${angle.toInt()}°", 100.0, 100.0)
    }


    override fun onMove(x: Double, y: Double) {
        super.onMove(x, y)
        lastX = x
        lastY = y
    }

    override fun isFinished() = false

    override fun getName() = "AngleTest"

    override fun getDesc() = "Click to match the given angle"

}

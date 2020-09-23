package com.github.se7_kn8.haa.units

import com.github.se7_kn8.haa.TestUnit
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.shape.ArcType
import javafx.scene.text.Font
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

class AngleUnit(maxRuns: Int) : TestUnit(maxRuns) {

    var lastX: Double = 0.0
    var lastY: Double = 0.0

    var givenAngle = 0.0
    var angle = 0.0

    override fun init() {
        givenAngle = Random.nextInt(0, 360).toDouble()
    }

    override fun drawFinish(gc: GraphicsContext) {
        super.drawFinish(gc)
        gc.stroke = Color.BLUE
        gc.strokeArc(hWidth() - 250.0, hHeight() - 250.0, 500.0, 500.0, 0.0, givenAngle, ArcType.ROUND)
        gc.fillText("Diff: " + getLastValue() + "° (" + angle.toInt() + "°)", 10.0, 100.0)
        gc.fillText("Avg: " + getDataAvg().toInt() + "°", 10.0, 150.0)
    }

    override fun draw(gc: GraphicsContext) {
        gc.fill = Color.BLACK
        gc.stroke = Color.BLACK
        gc.lineWidth = 5.0
        val x1 = width / 2.0
        val y1 = height / 2.0
        gc.font = Font(30.0)
        gc.fillText("The given angle is $givenAngle°", x1 - 50, height.toDouble() - 20.0)

        val vec1x = x1 - lastX
        val vec1y = y1 - lastY

        val length = sqrt(vec1x.pow(2.0) + vec1y.pow(2.0))

        val vec2x = length
        val vec2y = 0

        val dotP = vec1x * vec2x + vec1y * vec2y

        angle = 180.0 - Math.toDegrees(acos(dotP / (length * length)))

        if (vec1y < vec2y) {
            angle = 180 + (180 - angle)
        }

        gc.strokeArc(x1 - 200.0, y1 - 200.0, 400.0, 400.0, 0.0, angle, ArcType.ROUND)
    }

    override fun drawOverlay(gc: GraphicsContext) {
        gc.fillOval(hWidth() - 20, hHeight() - 20, 40.0, 40.0)
    }

    override fun handleMove(x: Double, y: Double) {
        lastX = x
        lastY = y
    }

    override fun handleClick(x: Double, y: Double) {
        finish(abs(givenAngle - angle).toInt())
    }

    override fun getName() = "AngleTest"

    override fun getDesc() = "Click to match the given angle"

}

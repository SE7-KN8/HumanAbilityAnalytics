package units

import TestUnit
import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.scene.text.Font
import kotlin.math.abs
import kotlin.random.Random

class ReactionUnit(maxRuns: Int, private val reactionType: ReactionType) : TestUnit(maxRuns) {

    enum class ReactionType {
        ONLY_BLACK,
        ONLY_GREY,
        ONLY_RED,
        ONLY_GREEN,
        ONLY_BLUE
    }


    private var color = Color.BLACK
    private var time: Long = 0
    private var timeToWait: Long = 0
    private var showTime: Long = 0

    private var timeSet = false

    override fun init() {
        timeToWait = getRuntime() + Random.nextLong(2000, 5000)
        setColor()
        timeSet = false
    }

    override fun drawUpdate(gc: GraphicsContext) {
        if (getRuntime() >= timeToWait) {
            if (!timeSet) {
                showTime = getRuntime()
                timeSet = true
            }
            gc.fill = color
            gc.fillRect(hWidth() - 300.0, hHeight() - 300.0, 600.0, 600.0)
        }
    }

    override fun drawFinish(gc: GraphicsContext) {
        super.drawFinish(gc)
        gc.font = Font.font(20.0)
        gc.fill = Color.BLACK
        gc.fillText(getLastValue().toString(), 100.0, 100.0)
        gc.fillText("Avg: " + getDataAvg(), 100.0, 150.0)
    }

    override fun handleKey(e: KeyCode) {
        time = abs(getRuntime() - showTime)
        finish(time.toInt())
    }

    private fun setColor() {
        when (reactionType) {
            ReactionType.ONLY_BLACK -> {
                color = Color.BLACK
            }
            ReactionType.ONLY_GREY -> {
                color = Color.LIGHTGREY
            }
            ReactionType.ONLY_BLUE -> {
                color = Color.BLUE
            }
            ReactionType.ONLY_GREEN -> {
                color = Color.GREEN
            }
            ReactionType.ONLY_RED -> {
                color = Color.RED
            }
        }
    }

    override fun getName() = "ReactionUnit_" + reactionType.name
    override fun getDesc() = "Press any key when the rectangle is visible"

}

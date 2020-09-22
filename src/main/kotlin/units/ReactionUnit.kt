package units

import DataUtil
import TestUnit
import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.scene.text.Font
import kotlin.random.Random

class ReactionUnit(private val maxRuns: Int, private val reactionType: ReactionType) : TestUnit() {

    enum class ReactionUnitStates {
        INIT,
        WAITING,
        SHOWING,
        FINISHING
    }

    enum class ReactionType {
        ONLY_BLACK,
        ONLY_GREY,
        ONLY_RED,
        ONLY_GREEN,
        ONLY_BLUE
    }

    private var currentRun = 0

    private var color = Color.BLACK
    private var currentState = ReactionUnitStates.INIT
    private var time: Long = 0
    private var timeToWait: Long = 0
    private var showTime: Long = 0

    private var finishTime: Long = 0

    private var times = ArrayList<Long>()

    override fun update(gc: GraphicsContext) {
        gc.fill = color
        when (currentState) {
            ReactionUnitStates.INIT -> {
                startTime = System.currentTimeMillis()
                timeToWait = Random.nextLong(2000, 5000)
                currentState = ReactionUnitStates.WAITING
                setColor()
            }
            ReactionUnitStates.SHOWING -> {
                val size = 600.0
                gc.fillRect(width.toDouble() / 2.0 - size / 2.0, height.toDouble() / 2.0 - size / 2.0, size, size)
            }
            ReactionUnitStates.WAITING -> {
                if (getRuntime() >= timeToWait) {
                    currentState = ReactionUnitStates.SHOWING
                    showTime = getRuntime()
                }
            }
            ReactionUnitStates.FINISHING -> {
                gc.font = Font.font(20.0)
                gc.fillText(times.joinToString(), 100.0, 100.0)
                gc.fillText("Avg: " + calcAvg(), 100.0, 150.0)
                if (getRuntime() >= finishTime) {
                    currentRun++
                    currentState = ReactionUnitStates.INIT
                }
            }
        }
    }

    override fun onKey(e: KeyCode) {
        if (currentState == ReactionUnitStates.SHOWING) {
            currentState = ReactionUnitStates.FINISHING
            finishTime = getRuntime() + 3000
            time = getRuntime() - showTime
            times.add(time)
        }
    }

    private fun setColor() {
        when (reactionType) {
            ReactionType.ONLY_BLACK -> {
                color = Color.BLACK
            }
            ReactionType.ONLY_GREY -> {
                color = Color.GRAY
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

    override fun saveResults(dataUtil: DataUtil) {
        dataUtil.saveTestResult(getName(), times.map { it.toInt() })
    }

    override fun isFinished(): Boolean {
        return currentRun == maxRuns
    }

    override fun getName() = "ReactionUnit_"+ reactionType.name
    override fun getDesc() = "Press any key when the rectangle is visible"

    private fun calcAvg(): Float {
        var value: Long = 0
        times.forEach { value += it }
        return value.toFloat() / times.size.toFloat()
    }

}

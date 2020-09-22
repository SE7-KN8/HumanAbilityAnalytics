package units

import TestUnit
import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.scene.text.Font
import kotlin.math.abs
import kotlin.random.Random

class CountdownUnit(maxRuns: Int) : TestUnit(maxRuns) {


    private var countdownLength = 0
    private var countdownPrivateLength = 0

    private var finishTime: Long = 0
    override fun init() {
        super.init()
        finishTime = 0
        countdownLength = Random.nextInt(9000, 15000)

        countdownPrivateLength = countdownLength - Random.nextInt(2000, countdownLength - 2000)
    }

    override fun drawUpdate(gc: GraphicsContext) {
        gc.fill = Color.BLACK
        gc.font = Font(25.0)
        if (countdownPrivateLength - getRuntime() > 0) {
            gc.fillText("" + ((countdownLength - getRuntime()) / 1000), hWidth(), hHeight())
        }else{
            gc.fillText("Press any key to end the countdown", hWidth(), hHeight())
        }
    }

    override fun drawFinish(gc: GraphicsContext) {
        super.drawFinish(gc)
        gc.fill = Color.BLACK
        gc.font = Font(25.0)
        gc.fillText("" + (finishTime - countdownLength).toInt(), 100.0, 100.0)
        gc.fillText("Avg. " + getDataAvg(), 100.0, 150.0)
    }

    override fun handleKey(e: KeyCode) {
        super.handleKey(e)
        finishTime = getRuntime()
        finish(abs((finishTime - countdownLength).toInt()))
    }


    override fun getName() = "ExtendCountdownUnit"

    override fun getDesc() = "See a countdown and press a key if the countdown is over"
}

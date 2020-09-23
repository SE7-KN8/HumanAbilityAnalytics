package com.github.se7_kn8.haa

import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.KeyCode

abstract class TestUnit(private val maxRuns: Int) {

    enum class TestUnitState {
        INIT,
        UPDATE,
        FINISH
    }

    var startTime: Long = 0
    var width = 0
    var height = 0
    var finishDuration = 3000
    var saveData = true
    private var finishTime: Long = 0
    private var currentRun: Int = 0

    private val data = ArrayList<Int>()
    private var currentState = TestUnitState.INIT


    open fun init() {
    }

    open fun draw(gc: GraphicsContext) {

    }

    open fun drawUpdate(gc: GraphicsContext) {

    }

    open fun drawFinish(gc: GraphicsContext) {

    }

    open fun drawOverlay(gc: GraphicsContext) {

    }

    fun finish(result: Int) {
        data.add(result)
        currentState = TestUnitState.FINISH
        finishTime = getRuntime() + finishDuration
    }

    fun update(gc: GraphicsContext) {
        draw(gc)
        when (currentState) {
            TestUnitState.INIT -> {
                startTime = System.currentTimeMillis()
                init()
                currentState = TestUnitState.UPDATE
            }
            TestUnitState.UPDATE -> {
                drawUpdate(gc)
            }
            TestUnitState.FINISH -> {
                drawFinish(gc)
                if (getRuntime() >= finishTime) {
                    currentRun++
                    currentState = TestUnitState.INIT
                }
            }
        }
        drawOverlay(gc)
    }

    fun saveResults(dataUtil: DataUtil) {
        if (saveData) {
            dataUtil.saveTestResult(getName(), data)
        }
    }

    fun getLastValue(): Int = data.last()

    fun getDataAvg(): Double {
        var value: Long = 0
        data.forEach { value += it }
        return value.toDouble() / data.size.toDouble()
    }

    fun getRuntime() = System.currentTimeMillis() - startTime

    fun isFinished() = currentRun == maxRuns

    abstract fun getName(): String

    abstract fun getDesc(): String


    fun onMove(x: Double, y: Double) {
        if (currentState == TestUnitState.UPDATE) {
            handleMove(x, y)
        }
    }

    fun onClick(x: Double, y: Double) {
        if (currentState == TestUnitState.UPDATE) {
            handleClick(x, y)
        }
    }

    fun onKey(e: KeyCode) {
        if (currentState == TestUnitState.UPDATE) {
            handleKey(e)
        }
    }

    open fun handleMove(x: Double, y: Double) {

    }

    open fun handleClick(x: Double, y: Double) {

    }

    open fun handleKey(e: KeyCode) {

    }


    fun hWidth() = width / 2.0

    fun hHeight() = height / 2.0

}

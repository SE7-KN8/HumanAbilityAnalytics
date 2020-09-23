package com.github.se7_kn8.haa.units

import com.github.se7_kn8.haa.TestUnit
import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.scene.text.Font
import kotlin.random.Random

class RememberUnit(maxRuns: Int, private val rememberUnitType: RememberUnitType) : TestUnit(maxRuns) {

    enum class RememberUnitType {
        TEXT_ONLY,
        NUMBERS_ONLY,
        TEXT_AND_NUMBERS
    }

    private val numbers = "0123456789"
    private val letters = "ABCDEFGHIJKLMNOPQRSTVUWXYZ"

    private val sequenceLength = 20


    private var endSequenceTime: Long = 0
    private var sequence = ArrayList<Char>()
    private var typedSequence = ArrayList<Char>()
    private var currentIndex = 0

    override fun init() {
        generateSequence()
        typedSequence.clear()
        currentIndex = 0
        endSequenceTime = getRuntime() + 20000
    }

    private fun generateSequence() {
        sequence.clear()
        for (i in 0 until sequenceLength) {
            when (rememberUnitType) {
                RememberUnitType.NUMBERS_ONLY -> {
                    sequence.add(numbers[Random.nextInt(0, numbers.length)])
                }
                RememberUnitType.TEXT_ONLY -> {
                    sequence.add(letters[Random.nextInt(0, letters.length)])
                }
                RememberUnitType.TEXT_AND_NUMBERS -> {
                    if (Random.nextDouble() < 0.5) {
                        sequence.add(numbers[Random.nextInt(0, numbers.length)])
                    } else {
                        sequence.add(letters[Random.nextInt(0, letters.length)])
                    }
                }
            }
        }
    }

    override fun drawUpdate(gc: GraphicsContext) {
        gc.font = Font(25.0)
        gc.fill = Color.BLACK
        if (getRuntime() >= endSequenceTime) {
            gc.fill = Color.GREEN
            gc.fillText(typedSequence.joinToString("   "), hWidth() - hWidth() / 2, hHeight())
        } else {
            gc.fillText(sequence.joinToString("   "), hWidth() - hWidth() / 2, hHeight())
            gc.fillText(
                "Time left: " + (endSequenceTime - getRuntime()) / 1000 + "s",
                hWidth() - hWidth() / 2,
                hHeight() + 200
            )
        }
    }

    override fun drawFinish(gc: GraphicsContext) {
        gc.font = Font(25.0)
        gc.fill = Color.RED
        gc.fillText(typedSequence.joinToString("   "), hWidth() - hWidth() / 2, hHeight())
        gc.fill = Color.GREEN
        gc.fillText(sequence.joinToString("   "), hWidth() - hWidth() / 2, hHeight() + 200)
        gc.fill = Color.BLACK
        gc.fillText("" + getLastValue(), 10.0, 100.0)
        gc.fillText("Avg. " + getDataAvg(), 10.0, 150.0)
    }

    override fun handleKey(e: KeyCode) {
        super.handleKey(e)
        if (e.isDigitKey || e.isLetterKey) {
            if (getRuntime() >= endSequenceTime) {
                typedSequence.add(e.char.toUpperCase()[0])
                if (e.char.toUpperCase()[0] == sequence[currentIndex]) {
                    currentIndex++
                    if (currentIndex == sequenceLength) {
                        finish(currentIndex)
                    }
                } else {
                    finish(currentIndex)
                }
            }
        }
    }

    override fun getName() = "RememberUnit_" + rememberUnitType.name

    override fun getDesc() = "Remember the sequence and type it when is appears"

}

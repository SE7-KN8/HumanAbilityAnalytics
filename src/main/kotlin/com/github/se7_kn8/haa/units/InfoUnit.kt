package com.github.se7_kn8.haa.units

import com.github.se7_kn8.haa.TestUnit
import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.scene.text.Font

class InfoUnit(private val text: String) : TestUnit(1) {

    override fun init() {
        finishDuration = 0
        saveData = false
    }

    override fun drawUpdate(gc: GraphicsContext) {
        super.drawUpdate(gc)
        gc.fill = Color.BLACK
        gc.font = Font(18.0)
        gc.fillText(text, hWidth() - hWidth() / 2.0, hHeight())
        gc.fillText("Press Space to continue!", hWidth(), hHeight() + 200)
    }

    override fun handleKey(e: KeyCode) {
        if (e == KeyCode.SPACE) {
            finish(-1)
        }
    }

    override fun getName() = "InfoUnit"

    override fun getDesc() = "Show a description of the next unit"
}

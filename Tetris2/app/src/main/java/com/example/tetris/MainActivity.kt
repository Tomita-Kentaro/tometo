/**
 * MainActivity
 */
package com.example.tetris

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import euniclus.tetris.*

class MainActivity : AppCompatActivity() {

    var graphic: Graphic? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        graphic = Graphic(this)
        setContentView(graphic!!)
    }

    // 画面がタップされたとき
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                val x = event.x
                val y = event.y
                if (x <= FIELDSTARTX) {
                    graphic?.updateBlockPos(-BLOCKSPACE, 0)
                } else if (x >= FIELDSTARTX + FIELDWIDTH) {
                    graphic?.updateBlockPos(BLOCKSPACE, 0)
                } else if (y >= FIELDSTARTY + FIELDHEIGHT) {
                    graphic?.updateBlockPos(0, BLOCKSPACE)
                } else {
                    graphic?.rotate()
                }
            }
        }
        return true
    }
}
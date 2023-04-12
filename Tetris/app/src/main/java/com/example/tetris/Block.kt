/**
 * Block
 */
package com.example.tetris

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import euniclus.tetris.BLOCKSIZE
import euniclus.tetris.BLOCKSTARTX
import euniclus.tetris.BLOCKSTARTY
import java.util.*

// ブロックに関する処理をする
class Block(val blockInfo: Array<IntArray>) {

    var xPos = BLOCKSTARTX
    var yPos = BLOCKSTARTY
    var paint: Paint = Paint()

    init {
        paint.style= Paint.Style.FILL
        paint.color= Color.argb(
            255,
            Random().nextInt(200),
            Random().nextInt(200),
            Random().nextInt(200))
    }

    fun draw(canvas: Canvas) {

        for (i in 0..blockInfo.size-1) {
            canvas.drawRect(Rect(
                xPos+blockInfo[i][0],
                yPos+blockInfo[i][1],
                xPos+ BLOCKSIZE +blockInfo[i][0],
                yPos+ BLOCKSIZE +blockInfo[i][1]),
                paint)
        }
    }

    fun updatePos(x: Int, y:Int) {
        xPos += x
        yPos += y
    }

    fun setPos(x: Int, y: Int) {
        xPos = x
        yPos = y
    }

}
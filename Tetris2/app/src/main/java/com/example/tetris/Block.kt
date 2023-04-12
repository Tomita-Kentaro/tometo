/**
 * Block
 */
package com.example.tetris

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.icu.text.UnicodeSet.EMPTY
import euniclus.tetris.*
import java.util.*

// ブロックに関する処理をする
class Block(var blockInfo: Array<IntArray>) {

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

    fun rotate(){
        // ブロックの形状を表す配列をコピーする
        val newBlockInfo = Array(blockInfo.size) { IntArray(2) }
        for (i in 0 until blockInfo.size) {
            newBlockInfo[i][0] = blockInfo[i][0]
            newBlockInfo[i][1] = blockInfo[i][1]
        }
        // 配列を反時計回りに90度回転させる
        for (i in 0 until blockInfo.size) {
            val x = newBlockInfo[i][0]
            val y = newBlockInfo[i][1]
            newBlockInfo[i][0] = -y
            newBlockInfo[i][1] = x
        }
        // 回転後の配列がブロックの形状として適切かどうかを確認する
        var isValid = true
        for (i in 0 until newBlockInfo.size) {
            val x = newBlockInfo[i][0]
            val y = newBlockInfo[i][1]
            if (xPos + x < FIELDSTARTX.toInt() ||
                xPos + x >= FIELDSTARTX.toInt() + FIELDWIDTH ||
                yPos + y < FIELDSTARTY.toInt() ||
                yPos + y >= FIELDSTARTY + FIELDHEIGHT ) {
                isValid = false
                break
            }
        }
        // 適切であれば、ブロックの形状を表す配列を更新する
        if (isValid) {
            blockInfo = newBlockInfo
        }
    }

    fun setPos(x: Int, y: Int) {
        xPos = x
        yPos = y
    }

}
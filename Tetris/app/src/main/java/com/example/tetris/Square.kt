/**
 * Square
 */

package com.example.tetris

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import euniclus.tetris.BLOCKSIZE

// フィールドに配置されているブロック
class Square(var xPos: Int, var yPos: Int, var paint: Paint) {

    fun draw(canvas: Canvas) {
        canvas.drawRect(
            Rect(
                xPos,
                yPos,
                xPos+ BLOCKSIZE,
                yPos+ BLOCKSIZE
            ),
            paint
        )
    }

}
/**
 * Field
 */

package com.example.tetris

import android.content.Context
import android.graphics.*
import android.view.WindowManager
import euniclus.tetris.*

// フィールドに関する処理をする
class Field {

    private var paint: Paint = Paint()
    private var blkOnField: Array<Square?> = Array(SQUARENUM, {null})

    // フィールドを表示する
    fun draw(canvas: Canvas, context: Context) {

        val wm: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val disp = wm.defaultDisplay
        val size = Point()
        disp.getSize(size)

        val startX = FIELDSTARTX
        val startY = FIELDSTARTY
        val height = FIELDHEIGHT
        val width = FIELDWIDTH
        val sqSize = SQUARESIZE
        for (i in 0..height/sqSize) {
            paint.style = Paint.Style.STROKE
            paint.color = Color.argb(255, 190,200,255)
            paint.strokeWidth = 10f
            canvas.drawLine(startX, startY+i*sqSize,startX+width,startY+i*sqSize, paint)

        }

        for (i in 0..width/sqSize) {
            paint.style = Paint.Style.STROKE
            paint.color = Color.argb(255, 190,200,255)
            paint.strokeWidth = 10f
            canvas.drawLine(startX+i*sqSize, startY,startX+i*sqSize,startY+height, paint)
        }
    }

    // ブロックの位置からインデックスを生成する
    fun checkBlockPos(block: Block) : Int {
        for (bi in block.blockInfo) {
            if (block.xPos < FIELDSTARTX.toInt()) {
                return CANTMOVE
            }
            val xInd = ( (block.xPos - FIELDSTARTX.toInt() + bi[0]) / SQUARESIZE )
            if (xInd >= FIELDWIDTH / SQUARESIZE) {
                return CANTMOVE
            }

            val yInd = ( (block.yPos - FIELDSTARTY.toInt() + bi[1]) / SQUARESIZE )
            if (yInd < 0) {
                continue
            }
            val index = xInd  + yInd * FIELDWIDTH / SQUARESIZE

            if (index >= SQUARENUM || blkOnField[index] != null) {
                return CANTMOVE
            }
        }
        return CANMOVE
    }

    // フィールドに表示するブロックを追加する
    fun addBlock(block: Block): Boolean {
        for (bi in block.blockInfo) {
            val xInd = ( (block.xPos - FIELDSTARTX.toInt() + bi[0]) / SQUARESIZE )
            val yInd = ( (block.yPos - FIELDSTARTY.toInt() + bi[1]) / SQUARESIZE )

            if (yInd < 0) {
                return false
            }

            val index = xInd  + yInd * FIELDWIDTH / SQUARESIZE

            blkOnField[index] = Square(
                block.xPos+bi[0],
                block.yPos+bi[1],
                block.paint)

        }

        updateBlkOnField()
        return true
    }

    // フィールドに配置されているブロックを表示する
    fun drawBlkOnField(canvas: Canvas) {
        for (blk in blkOnField) {
            if (blk != null) {
                blk.draw(canvas)
            }

        }
    }

    // フィールドに配置されているブロックを更新する
    fun updateBlkOnField() {
        val h = FIELDHEIGHT / SQUARESIZE
        val w = FIELDWIDTH / SQUARESIZE

        for (i in h-1 downTo 0) {
            var cnt = 0
            for ( j in 0..w-1) {
                if (blkOnField[j+i*w] != null) {
                    cnt += 1
                }
            }
            if (cnt == w) {
                for (k in i-1 downTo 0) {
                    for (l in 0..w-1) {
                        if (blkOnField[k*w+l] != null) {
                            blkOnField[k*w+l]!!.yPos += BLOCKSPACE
                        }

                        blkOnField[(k+1)*w+l] = blkOnField[k*w+l]
                        blkOnField[k*w+l] = null
                    }
                }
            }
        }
    }

    // フィールドの状態を初期化
    fun initField() {
        blkOnField = Array(SQUARENUM, {null})
    }

}
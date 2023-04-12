/**
 * Graphic
 */
package com.example.tetris

import android.content.Context
import android.graphics.*
import android.view.View
import euniclus.tetris.*
import java.util.*

// 図形の表示に関する処理を行うクラス
class Graphic(context: Context): View(context) {
    var block = Block(BlockTypeC)
    val field = Field()
    var blockSleepCnt = 0

    override fun onDraw(canvas: Canvas) {
        // フィールドを表示
        drawField(canvas)

        // ブロックを表示
        drawBlock(canvas)

        reDraw()
    }



    private fun reDraw() {
        invalidate()
    }

    // ブロックを表示
    private fun drawBlock(canvas: Canvas) {
        blockSleepCnt += 1
        if (blockSleepCnt > BLOCKDOWNCNT) {
            updateBlockPos(0, BLOCKSPACE)
            blockSleepCnt = 0
        }

        block.draw(canvas)
    }

    // ブロックを生成
    private fun makeBlock() {
        val type = Random().nextInt(4)
        when(type) {
            0 -> block = Block(BlockTypeA)
            1 -> block = Block(BlockTypeB)
            2 -> block = Block(BlockTypeC)
            3 -> block = Block(BlockTypeD)
        }

    }

    // フィールドを表示
    private fun drawField(canvas: Canvas) {
        field.drawBlkOnField(canvas)
        field.draw(canvas, context)
    }

    // ブロックの位置を更新
    fun updateBlockPos(x: Int, y: Int) {
        val tempX = block.xPos
        val tempY = block.yPos
        block.updatePos(x,y)
        if (field.checkBlockPos(block) == CANTMOVE) {
            block.setPos(tempX, tempY)
            if (y != 0) {
                if (field.addBlock(block) == false) {
                    field.initField()
                }
                makeBlock()
            }
        }
    }

    fun rotate() {
        block.rotate()
    }

}
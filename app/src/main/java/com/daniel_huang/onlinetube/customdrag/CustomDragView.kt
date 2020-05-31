package com.daniel_huang.onlinetube.customdrag

import android.content.Context
import android.util.AttributeSet
import com.tuanhav95.drag.DragView
import com.tuanhav95.drag.utils.inflate
import com.daniel_huang.onlinetube.R
import com.tuanhav95.drag.utils.reWidth
import kotlinx.android.synthetic.main.layout_player.view.*

class CustomDragView @JvmOverloads
    constructor(context : Context , attrs : AttributeSet? = null , defStyleAttr : Int = 0)
    : DragView(context, attrs,defStyleAttr) {

    var mMaxWidth = 0
    var mMiddleWidth = 0
    var mMinWidth = 0

    val widthPercent = 15
    val heightPercent = 9

    init {
        getFrameFirst().addView(inflate(R.layout.layout_player))
        getFrameSecond().addView(inflate(R.layout.layout_player_background))
    }

    override fun initFrame() {
        mMaxWidth = width
        mMiddleWidth = (width - mPercentWhenMiddle * mMarginEdgeWhenMin).toInt()
        mMinWidth = mHeightWhenMin * widthPercent / heightPercent
        super.initFrame()
    }

    override fun refreshFrameFirst() {
        super.refreshFrameFirst()
        val width = if (mCurrentPercent < mPercentWhenMiddle){
            (mMaxWidth - (mMaxWidth - mMiddleWidth) * mCurrentPercent)
        }else{
            (mMiddleWidth - (mMiddleWidth - mMinWidth) * (mCurrentPercent - mPercentWhenMiddle) / (1 - mPercentWhenMiddle))
        }
        player_frame.reWidth(width.toInt())
    }

}
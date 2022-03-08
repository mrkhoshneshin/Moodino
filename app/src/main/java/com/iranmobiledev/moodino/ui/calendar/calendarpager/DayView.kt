package com.iranmobiledev.moodino.ui.calendar.calendarpager

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.iranmobiledev.moodino.utlis.sp
import kotlin.math.min

class DayView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var text = ""
    private var dayIsSelected = false
    private var today = false
    private var holiday = false
    var jdn: Jdn? = null
        private set
    var dayOfMonth = -1
        private set
    private var isWeekNumber = false
    private var header = ""

    var sharedDayViewData: SharedDayViewData? = null

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val shared = sharedDayViewData ?: return

        val radius = min(width, height) / 2f
        drayCircle(canvas, shared, radius) // background circle if is needed
        drawText(canvas, shared)
        drawHeader(canvas, shared)

    }

    private fun drayCircle(canvas: Canvas?, shared: SharedDayViewData, radius: Float) {
        if (dayIsSelected) canvas?.drawCircle(
            width / 2f, height / 2f, radius, shared.selectedPaint
        )

        if (today) canvas?.drawCircle(
            width / 2f, height / 2f, radius, shared.todayPaint
        )
    }

    private val textBounds= Rect()
    private fun drawText(canvas: Canvas?, shared: SharedDayViewData) {
        val textPain= shared.weekDayInitialsTextPaint

        textPain.getTextBounds(text, 0 , text.length , textBounds)
        val yPos= (height + textBounds.height()) / 2f
        canvas?.drawText(text, width/2f, yPos + 3.sp, textPain)
    }

    private fun drawHeader(canvas: Canvas?, shared: SharedDayViewData){
        if (header.isEmpty()) return
        canvas?.drawText(header ,width/2f, height * 0.26f
            , if(dayIsSelected) shared.headerTextSelectedPaint else shared.headerTextPaint )
    }

    private fun setAll(
        text: String,
        isToday: Boolean = false,
        isSelected: Boolean = false,
        isHoliday: Boolean = false,
        jdn: Jdn? = null,
        dayOfMonth: Int = -1,
        header: String = "",
        isWeekNumber: Boolean = false
    ) {
        this.text= text
        this.today= isToday
        this.isSelected= isSelected
        this.holiday= isHoliday
        this.jdn= jdn
        this.dayOfMonth= dayOfMonth
        this.header= header
        this.isWeekNumber= isWeekNumber

        postInvalidate()
    }
}
package com.baton.graphic

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.TintTypedArray

class PieChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var components: List<Component> = mutableListOf()
    private var pieMargins: Float = 0F
    private val rectF: RectF = RectF()
    private val paint: Paint = Paint()

    var values: List<Segment> = listOf()
        set(value) {
            field = value

            val valuesSum = value.map { it.value }.sum()

            components = value.map {
                Component(
                    it.value.getPercentOf(valuesSum),
                    it.color
                )
            }
        }

    init {
        val a = attrs?.let {
            TintTypedArray.obtainStyledAttributes(
                context,
                attrs,
                R.styleable.PieChart,
                defStyleAttr,
                defStyleAttr
            )
        }

        try {
            a?.let {
                pieMargins = it.getDimension(R.styleable.PieChart_segment_margin, 0F)
            }
        } finally {
            a?.recycle()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        rectF.set(
            0F + paddingStart,
            0F + paddingTop,
            width.toFloat() - paddingEnd,
            height.toFloat() - paddingBottom
        )

        var start = 0F
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        paint.strokeWidth = 1f

        for (segment in components) {
            paint.color = segment.color ?: Color.BLUE
            val sweep = (360 - (values.size) * pieMargins) * segment.value.toFloat()
            canvas?.drawArc(rectF, start, sweep, true, paint)
            start += sweep + pieMargins
        }
    }

    private fun Double.getPercentOf(sum: Double): Double {
        if (sum == 0.0) return 0.0
        return 100F / sum * this / 100
    }
}

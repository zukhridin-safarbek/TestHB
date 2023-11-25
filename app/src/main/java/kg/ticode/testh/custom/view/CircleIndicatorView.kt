package kg.ticode.testh.custom.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CircleIndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var numIndicators = 0
    private var selectedPosition = 0
    private val gray = Color.argb(255, 212, 212, 212)

    init {
        paint.style = Paint.Style.FILL
        paint.color = gray
    }

    fun setIndicators(numIndicators: Int) {
        this.numIndicators = numIndicators
        requestLayout()
    }

    fun setSelectedPosition(position: Int) {
        this.selectedPosition = position
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = paddingLeft + paddingRight + calculateTotalWidth()
        val height = paddingTop + paddingBottom + (2 * radius).toInt()
        setMeasuredDimension(
            resolveSize(width, widthMeasureSpec),
            resolveSize(height, heightMeasureSpec)
        )
    }

    private fun calculateTotalWidth(): Int {
        return ((numIndicators - 1) * 3 * radius + 2 * radius).toInt()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0 until numIndicators) {
            val cx = paddingLeft + radius + i * 3 * radius
            val cy = height / 2f
            paint.color = Color.WHITE
            if (i == selectedPosition) paint.color = Color.BLACK else paint.color = gray
            canvas.drawCircle(cx, cy, radius, paint)
        }
    }

    companion object {
        private const val radius = 7f
    }
}
package com.example.qrscannerexample;

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import kotlin.jvm.JvmOverloads;

class ScannerOverlay : ViewGroup {
    private var left = 0f
    private var top = 0f
    private var endY = 0f
    private var rectWidth = 0
    private var rectHeight = 0
    private var frames = 0
    private val revAnimation = false
    private var lineColor = 0
    private var lineWidth = 0

    constructor(context: Context?) : super(context) {}

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int = 0) : super(
        context,
        attrs,
        defStyle
    ) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ScannerOverlay,
            0, 0
        )
        rectWidth = a.getInteger(
            R.styleable.ScannerOverlay_square_width,
            resources.getInteger(R.integer.scanner_rect_width)
        )
        rectHeight = a.getInteger(
            R.styleable.ScannerOverlay_square_height,
            resources.getInteger(R.integer.scanner_rect_height)
        )
        lineColor = a.getColor(
            R.styleable.ScannerOverlay_line_color,
            ContextCompat.getColor(context, R.color.design_pink)
        )
        lineWidth = a.getInteger(
            R.styleable.ScannerOverlay_line_width,
            resources.getInteger(R.integer.line_width)
        )
        frames = a.getInteger(
            R.styleable.ScannerOverlay_line_speed,
            resources.getInteger(R.integer.line_width)
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    public override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {}
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        left = ((w - dpToPx(rectWidth)) / 2).toFloat()
        top = ((h - dpToPx(rectHeight)) / 2).toFloat()
        endY = top
        super.onSizeChanged(w, h, oldw, oldh)
    }

    fun dpToPx(dp: Int): Int {
        val displayMetrics = resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    override fun shouldDelayChildPressedState(): Boolean {
        return false
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // draw transparent rect
        val cornerRadius = 0
        val eraser = Paint()
        eraser.isAntiAlias = true
        eraser.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        val rect = RectF(left, top, dpToPx(rectWidth) + left, dpToPx(rectHeight) + top)
        canvas.drawRoundRect(rect, cornerRadius.toFloat(), cornerRadius.toFloat(), eraser)
        val angle = Paint()
        angle.color = lineColor
        angle.strokeWidth = dpToPx(4).toFloat()
        canvas.drawLine(
            rect.left - angle.strokeWidth / 2,
            rect.top,
            rect.left + dpToPx(41),
            rect.top,
            angle
        )
        canvas.drawLine(
            rect.left,
            rect.top - angle.strokeWidth / 2,
            rect.left,
            rect.top + dpToPx(41),
            angle
        )
        canvas.drawLine(
            rect.left - angle.strokeWidth / 2,
            rect.bottom,
            rect.left + dpToPx(41),
            rect.bottom,
            angle
        )
        canvas.drawLine(
            rect.left,
            rect.bottom - angle.strokeWidth / 2,
            rect.left,
            rect.bottom - dpToPx(40),
            angle
        )
        canvas.drawLine(
            rect.right + angle.strokeWidth / 2,
            rect.top,
            rect.right - dpToPx(41),
            rect.top,
            angle
        )
        canvas.drawLine(
            rect.right,
            rect.top + angle.strokeWidth / 2,
            rect.right,
            rect.top + dpToPx(41),
            angle
        )
        canvas.drawLine(
            rect.right + angle.strokeWidth / 2,
            rect.bottom,
            rect.right - dpToPx(41),
            rect.bottom,
            angle
        )
        canvas.drawLine(
            rect.right,
            rect.bottom + angle.strokeWidth / 2,
            rect.right,
            rect.bottom - dpToPx(41),
            angle
        )

//        // draw horizontal line
//        Paint line = new Paint();
//        line.setColor(lineColor);
//        line.setStrokeWidth(Float.valueOf(lineWidth));
//
//        // draw the line to product animation
//        if (endY >= top + dpToPx(rectHeight) + frames) {
//            revAnimation = true;
//        } else if (endY == top + frames) {
//            revAnimation = false;
//        }
//
//        // check if the line has reached to bottom
//        if (revAnimation) {
//            endY -= frames;
//        } else {
//            endY += frames;
//        }
//        canvas.drawLine(left, endY, left + dpToPx(rectWidth), endY, line);
//        invalidate();
    }
}


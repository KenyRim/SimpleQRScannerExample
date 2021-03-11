package com.example.qrscannerexample.custom_camera;

import android.graphics.Canvas
import android.graphics.Paint
import com.google.android.gms.vision.barcode.Barcode


class BarcodeGraphic internal constructor(overlay: GraphicOverlay<BarcodeGraphic>) : GraphicOverlay.Graphic(overlay) {
    var id = 0
    private val mRectPaint: Paint = Paint()

    @Volatile
    var barcode: Barcode? = null
        private set

    /**
     * Updates the barcode instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    fun updateItem(barcode: Barcode?) {
        this.barcode = barcode
        postInvalidate()
    }

    /**
     * Draws the barcode annotations for position, size, and raw value on the supplied canvas.
     */
    override fun draw(canvas: Canvas?) {
        val barcode = barcode ?: return

//        // Draws the bounding box around the barcode.
//        RectF rect = new RectF(barcode.getBoundingBox());
//        rect.left = translateX(rect.left);
//        rect.top = translateY(rect.top);
//        rect.right = translateX(rect.right);
//        rect.bottom = translateY(rect.bottom);
//        canvas.drawRect(rect, mRectPaint);
    }

    companion object {
        private const val COLOR_CHOICE = -0x1eff7f
    }

    init {
        mRectPaint.color = COLOR_CHOICE
        mRectPaint.style = Paint.Style.STROKE
        mRectPaint.strokeWidth = 4.0f
    }
}


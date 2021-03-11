package com.example.qrscannerexample.custom_camera

import android.content.Context
import com.google.android.gms.vision.MultiProcessor
import com.google.android.gms.vision.barcode.Barcode

class BarcodeTrackerFactory(
    var mGraphicOverlay: GraphicOverlay<BarcodeGraphic>,
    var mContext: Context
) : MultiProcessor.Factory<Barcode> {

    override fun create(barcode: Barcode): BarcodeGraphicTracker {
        val graphic = BarcodeGraphic(mGraphicOverlay)
        return BarcodeGraphicTracker(mGraphicOverlay, graphic, mContext)
    }

    init {
        this.mGraphicOverlay = mGraphicOverlay
        this.mContext = mContext
    }



}


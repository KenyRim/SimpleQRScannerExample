package com.example.qrscannerexample

import android.Manifest
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Detector.Detections
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var before = ""
    lateinit var cameraSource: CameraSource

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initScanner()
        cameraView.setOnClickListener{
            initScanner()
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 1)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.CAMERA
                    ) != PERMISSION_GRANTED
                ) {
                    return
                }else {
                    cameraSource.start(cameraView.holder)
                }
            }
            else -> finish()
        }
    }



    private fun initScanner() {
        barcodeInfo.text = "Scanning..."
        //val detector = BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build()
        val barcodeDetector1 = BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build()
        val barcodeDetector = BoxDetector(barcodeDetector1, 700, 600)


        cameraSource = CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(1600, 1024)
            .setAutoFocusEnabled(true).build()

        cameraView.holder.addCallback(object : SurfaceHolder.Callback {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun surfaceCreated(holder: SurfaceHolder) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 1)
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })

        barcodeDetector.setProcessor(object : Detector.Processor<Barcode?> {
            override fun release() {
            }

            override fun receiveDetections(detections: Detections<Barcode?>) {
                val barcodes = detections.detectedItems


                if (barcodes.size() != 0) {
                    val result = barcodes.valueAt(0)?.displayValue.toString()

                    if (result != before) {
                        runOnUiThread {
                            barcodeInfo.text = result
                            cameraSource.stop()
                            cameraSource.release()
                        }
                    }
                }
            }
        })
    }
}



package com.example.qrscannerexample

import android.Manifest
import android.content.pm.PackageManager
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
import java.io.IOException

class MainActivity : AppCompatActivity() {

    var before = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initScanner()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun permissionCheck(cameraSource: CameraSource) {
        try {
            when (ActivityCompat.checkSelfPermission(baseContext, Manifest.permission.CAMERA)) {
                PackageManager.PERMISSION_GRANTED -> cameraSource.start(cameraView.holder)
                else -> requestPermissions(arrayOf(Manifest.permission.CAMERA), 1)
            }
        } catch (e: IOException) {
            Log.e("kenyrim", e.message.toString())
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> initScanner()

        }
    }

    private fun initScanner() {
        val detector = BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build()
        val cameraSource = CameraSource.Builder(this, detector).setRequestedPreviewSize(640, 480).setAutoFocusEnabled(true).build()

        cameraView.holder.addCallback(object : SurfaceHolder.Callback {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun surfaceCreated(holder: SurfaceHolder) {
                permissionCheck(cameraSource)
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })

        detector.setProcessor(object : Detector.Processor<Barcode?> {
            override fun release() {
            }
            override fun receiveDetections(detections: Detections<Barcode?>) {
                val barcodes = detections.detectedItems


                if (barcodes.size() != 0) {
                    val result = barcodes.valueAt(0)?.displayValue.toString()

                    if (result != before) {
                        runOnUiThread {
                            barcodeInfo.text = result
                            before = result

                            Log.e("kenyrim", barcodes.valueAt(0)?.displayValue.toString())
                        }
                    }
                }
            }
        })
    }
}
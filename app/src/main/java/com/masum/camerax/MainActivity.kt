package com.masum.camerax

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.TextureView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor
import java.util.concurrent.Executors


private const val REQUEST_CODE_PERMISION = 10
private val REQUIRED_PERMISION = arrayOf(Manifest.permission.CAMERA)

class MainActivity : AppCompatActivity() {

    private val executor = Executors.newSingleThreadExecutor();
    private lateinit var viewFinder: TextureView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewFinder = findViewById(R.id.view_finder)

        if (allPermissionGranted()) {
            viewFinder.post { startCamera() }
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISION, REQUEST_CODE_PERMISION)
        }

        viewFinder.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            updateTransform()
        }
    }


    private fun startCamera() {

    }

    private fun updateTransform() {

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISION) {
            if (allPermissionGranted()) {
                viewFinder.post { startCamera() }
            } else
                Toast.makeText(
                    applicationContext,
                    "permission is not granted by the user",
                    Toast.LENGTH_LONG
                ).show()
        }
    }


    private fun allPermissionGranted() = REQUIRED_PERMISION.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
}

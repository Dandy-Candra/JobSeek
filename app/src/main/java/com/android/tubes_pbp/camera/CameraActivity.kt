package com.android.tubes_pbp.camera

import android.Manifest
import android.annotation.SuppressLint
import android.hardware.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.Toast
import com.android.tubes_pbp.R
import com.vmadalin.easypermissions.EasyPermissions

class CameraActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    companion object {
        private const val TAG = "CameraActivity"
        private const val CAMERA_PERMISSION_REQUEST_CODE = 200
    }
    private var mCamera: Camera? = null
    private var mCameraView: CameraView? = null
    @SuppressLint("LocalSuppress")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        getSupportActionBar()?.hide()

        requestCameraPermission()


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun hasCameraPermission() = EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)

    private fun requestCameraPermission() = EasyPermissions.requestPermissions(
        this,
        "Untuk Menggunakan Kamera, Izinkan Aplikasi Ini",
        CAMERA_PERMISSION_REQUEST_CODE,
        Manifest.permission.CAMERA
    )

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        Toast.makeText(this, "Izin Kamera Ditolak", Toast.LENGTH_SHORT).show()

    }

    @SuppressLint("LocalSuppress")
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
        try{
            mCamera = Camera.open()
        }catch (e: Exception){
            Log.d("Error", "Failed to get camera " + e.message)
        }

        if(mCamera != null){
            mCameraView = CameraView(this, mCamera!!)
            val camera_view = findViewById<View>(R.id.FLCamera) as FrameLayout
            camera_view.addView(mCameraView)
        }

        @SuppressLint("MissingInflatedId", "LocalSupress") val imageClose =
            findViewById<View>(R.id.imgClose) as ImageButton
        imageClose.setOnClickListener{
            mCamera?.release()
            finish()
        }
    }
}
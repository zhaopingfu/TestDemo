package com.zhaopf.screenshot

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker

class MainActivity : AppCompatActivity() {

    private lateinit var screenShotListener: ScreenShotListener
    var isHasScreenShotListener = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        screenShotListener = ScreenShotListener.getInstance(this)

        // 申请权限
        val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, permission, 1001)
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001) {
            if (grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
                customToast("权限申请成功")
            } else {
                customToast("权限申请失败")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        startScreenShotListen()
    }

    override fun onPause() {
        super.onPause()
        stopScreenShotListen()
    }

    private fun startScreenShotListen() {
        if (!isHasScreenShotListener) {
            screenShotListener.setListener(object : ScreenShotListener.OnScreenShotListener {
                override fun onScreenShot(picPath: String) {
                    customToast("监听截屏成功")
                    Log.d(ScreenShotHelper.TAG, "==== picPath: $picPath")
                }
            })
            screenShotListener.startListener()
            isHasScreenShotListener = true
        }
    }

    private fun stopScreenShotListen() {
        if (isHasScreenShotListener) {
            screenShotListener.stopListener()
            isHasScreenShotListener = false
        }
    }

    private fun customToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }
}

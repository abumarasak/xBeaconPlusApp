package com.xuntong.xbeaconplus

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        checkPermission()
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ApplyPermissionUtil.newUtil().requestPermissions(
                this,
                {
                    //权限拥有，检查蓝牙是否开启
                    if (!Utils.isBleEnabled()) {
                        ToastUtils.showLong("请打开蓝牙")
                    } else {
                       ActivityUtils.startActivity(MainActivity::class.java)
                    }
                },
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA
            )
        } else {
            ApplyPermissionUtil.newUtil().requestPermissions(
                this, {
                    //权限拥有，检查蓝牙是否开启
                    if (!Utils.isBleEnabled()) {
                        ToastUtils.showLong("请打开蓝牙")
                    } else {
                        ActivityUtils.startActivity(MainActivity::class.java)
                    }
                }, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
        }
    }
}
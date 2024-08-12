package com.xuntong.xbeaconplus

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import com.afollestad.materialdialogs.util.DialogUtils
import com.blankj.utilcode.util.ToastUtils
import com.tbruyelle.rxpermissions.RxPermissions

/**
 * 申请权限工具类
 */
class ApplyPermissionUtil {
    private var rxPermission: RxPermissions? = null
    private val TAG = javaClass.name
    fun requestPermissions(
        activity: Activity?,
        bListener: () -> Unit,
        vararg permissions: String?
    ) {
        if (rxPermission == null) {
            rxPermission = RxPermissions((activity as FragmentActivity?)!!)
        }

        rxPermission!!
            .requestEach(
                *permissions
            )
            .subscribe { permission ->
                when {
                    permission.granted -> {
                        bListener.invoke()
                    }
                    permission.shouldShowRequestPermissionRationale -> {
                        // Denied permission without ask never again
                        ToastUtils.showLong("权限被拒")
                    }
                    else -> {
                        // Denied permission with ask never again
                        // Need to go to the settings
                        ToastUtils.showLong("权限被拒")
                    }
                }
            }

    }


    companion object {
        fun newUtil(): ApplyPermissionUtil {
            return ApplyPermissionUtil()
        }
    }
}
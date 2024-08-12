package com.xuntong.xbeaconplus

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.provider.Settings
import android.text.InputType
import com.afollestad.materialdialogs.MaterialDialog
import com.kongzue.dialogx.dialogs.InputDialog
import com.kongzue.dialogx.dialogs.MessageDialog
import com.kongzue.dialogx.dialogs.WaitDialog
import com.kongzue.dialogx.interfaces.BaseDialog
import com.kongzue.dialogx.interfaces.OnInputDialogButtonClickListener
import com.kongzue.dialogx.util.InputInfo
import com.kongzue.dialogx.util.TextInfo
import com.xuntong.xbeaconplus.Utils.isLocationPermissionDeniedForever

object XtDialogUtils {
    var permissionTipdialog: MaterialDialog? = null
    var permissionErrordialog: MaterialDialog? = null

    //权限被拒绝，且没有勾选不在询问
    fun showPermissionTipdialog(activity: Activity) {
        if (permissionTipdialog == null) {
            permissionTipdialog =
                MaterialDialog.Builder(activity).title(R.string.permission_dialog_title)
                    .content(R.string.permission_dialog_message)
                    .positiveText(R.string.go_on)
                    .cancelable(false)
                    .onPositive { dialog, _ ->
                        Utils.isLocationPermissionDeniedForever(activity)
                        dialog.dismiss()
                    }.build()
            permissionTipdialog?.show()
        } else {
            permissionTipdialog?.show()
        }
    }

    fun hidePermissionTipdialog() {
        permissionTipdialog?.dismiss()
    }

    //权限被拒绝，且勾选不在询问，需要跳转到设置
    fun showPermissionErrordialog(activity: Activity) {
        if (permissionErrordialog == null) {
            permissionErrordialog =
                MaterialDialog.Builder(activity).title(R.string.permission_dialog_title)
                    .content(R.string.permission_dialog_error)
                    .positiveText(R.string.setting_title)
                    .cancelable(false)
                    .onPositive { dialog, which -> activity.startActivity(Intent(Settings.ACTION_SETTINGS)) }
                    .negativeText(R.string.cancel)
                    .build()
            permissionErrordialog?.show()
        } else {
            permissionErrordialog?.show()
        }
    }

    fun hidePermissionErrordialog() {
        permissionErrordialog?.dismiss()
    }

    var inputDialog:InputDialog?=null
    fun showEditDialog(
        title: String,
        message: String,
        sure: String,
        cancel: String,
        lastText: String,
        inputTypeIsNumber:Boolean=false,
        hint:String="",
        result: (text: String) -> Unit,
    ) {
        inputDialog = InputDialog.build()
            .setTitle(title)
            .setMessage(message)
            .setCancelButton(cancel)
            .setInputText(lastText)
            .setCancelable(false)
            .setInputHintText(hint)
            .setOkButton(sure) { baseDialog, v, inputStr ->
                result.invoke(inputStr)
                true
            }
        if(inputTypeIsNumber){
            inputDialog?.inputInfo = InputInfo().setInputType(InputType.TYPE_CLASS_NUMBER)
        }else{
            inputDialog?.inputInfo = InputInfo().setInputType(InputType.TYPE_CLASS_TEXT)
        }
//        inputDialog?.okTextInfo?.fontColor = android.R.color.holo_blue_light
        inputDialog?.isAutoShowInputKeyboard = true
        inputDialog?.show()
        inputDialog?.okTextInfo?.fontColor = android.R.color.holo_blue_light
    }

    fun hideInputDialog(){
        inputDialog?.dismiss()
    }

    fun showNormalDialog(
        title: String,
        message: String,
        sure: String,
        cancel: String,
        sureClick: () -> Unit
    ) {
        MessageDialog.show(title, message, sure, cancel)
            .setOkButton { baseDialog, v ->
                sureClick.invoke()
                false
            }.isCancelable = false
    }

    fun showNormalDialog(
        title: String,
        message: String,
        sure: String,
        cancel: String,
        sureClick: () -> Unit,
        cancelClick: () -> Unit
    ) {
        MessageDialog.show(title, message, sure, cancel)
            .setOkButton { baseDialog, v ->
                sureClick.invoke()
                false
            }
            .setCancelButton{ baseDialog, v ->
                cancelClick.invoke()
                false
            }.isCancelable = false
    }

    fun showWarnDialog(
        title: String,
        message: String,
        sure: String,
        cancel: String,
        sureClick: () -> Unit
    ) {
        MessageDialog.show(title, message, sure, cancel)
            .setOkButton { baseDialog, v ->
                sureClick.invoke()
                false
            }.okTextInfo = TextInfo().setFontColor(Color.RED)
    }

    fun showLoading(title: String) {
        WaitDialog.show(title)
        Handler().postDelayed(Runnable {
            if (WaitDialog.getInstance().isShow) {
                WaitDialog.dismiss()
            }
        }, 5000)
    }

    /**
     * pro 进度（0-100）
     */
    fun showProgress(title: String, pro: Int) {
        var progress = pro / 100f
        WaitDialog.show(title, progress)
    }

    fun hideLoadingOrProgress() {
        WaitDialog.dismiss()
    }
}
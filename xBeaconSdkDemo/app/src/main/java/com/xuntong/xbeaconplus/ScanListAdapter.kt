package com.xuntong.xbeaconplus

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.xuntong.xbeaconsdk.open.DeviceType
import com.xuntong.xbeaconsdk.open.DeviceType.isCard
import com.xuntong.xbeaconsdk.open.ScanedBluetoothDevice
import com.xuntong.xbeaconsdk.open.XtManager

class ScanListAdapter(data: MutableList<ScanedBluetoothDevice>?) :
    BaseQuickAdapter<ScanedBluetoothDevice, BaseViewHolder>(R.layout.item_scan) {
    override fun convert(helper: BaseViewHolder, item: ScanedBluetoothDevice) {
        item.run {
            val rssiIcon = helper.getView<ImageView>(R.id.rssiIv)
            if (XtManager.getInstance().isCard(devType)) {
                rssiIcon.setImageResource(R.mipmap.device_card_open)
            }
            rssiIcon.setImageLevel(((100.0f * (127.0f + rssi) / (127.0f + 20.0f)).toInt()))
            helper.setText(R.id.rssiTv, rssi.toString())
                .setText(
                    R.id.name,
                    (name ?: "unknown") + "：" + XtManager.getInstance().getTypeName(
                        devType
                    )
                )
                .setText(R.id.address, address)

            helper.setText(R.id.versionTv, "V$fVer")
            helper.setVisible(R.id.versionTv, true)
            helper.setVisible(R.id.divideLineAdd, true)

            helper.setText(R.id.battery, "Bat:$battery%")
                .setText(R.id.major, (if(major==-1)"- - -" else "Maj:"+major))
                .setText(R.id.minor, (if(minor==-1)"- - -" else "Min:"+minor))

            //新P2系列状态显示sensor
            when (devType) {
                DeviceType.TYPE_ZZ_AOA, DeviceType.TYPE_ZZ_P1,
                DeviceType.TYPE_Z4_BETA, DeviceType.TYPE_Z4_M, DeviceType.TYPE_DAXI,
                DeviceType.TYPE_Z4_AOA_BETA,DeviceType.TYPE_Z4_AOA_TTSF,DeviceType.TYPE_Z4_AOA,DeviceType.TYPE_Z4_AOA_LC,DeviceType.TYPE_Z4
                -> {
                    sensorData?.let {
                            helper.setText(R.id.stateTv, getSensorDataDescribe(it))
                                .setVisible(R.id.stateTv, true)
                                .setVisible(R.id.divideLineState, true)

                    }
                }
                DeviceType.TYPE_ROAD_MT_V2 -> {
                    helper.setText(R.id.major, "Gap:$gap")
                        .setText(R.id.minor, "Power:$txPower")
                }
                else -> {
                    if (isCard(devType)) {
                        sensorData?.let {
                                helper.setText(R.id.stateTv, getSensorDataDescribe(it))
                                    ?.setVisible(R.id.stateTv, true)
                                    ?.setVisible(R.id.divideLineState, true)
                        }
                    }else{
                        helper.setVisible(R.id.stateTv, false).setVisible(R.id.divideLineState, false)
                    }
                }
            }

            sensorData?.let {
                helper.setText(R.id.stateTv, getSensorDataDescribe(it))
                    .setVisible(R.id.stateTv, true)
                    .setVisible(R.id.divideLineState, true)
            }
        }
    }

    fun getSensorDataDescribe(code: Int): String {
        when (code) {
            //未运动，未脱离
            1 -> {
                return context.getString(R.string.still)
            }
            //未运动，脱离
            2 -> {
                return context.getString(R.string.fall)
            }
            //运动，未脱离
            3 -> {
                return context.getString(R.string.move)
            }
            //运动，脱离
            0xc3 -> {
                return context.getString(R.string.move_and_fall)
            }
            //未运动、SOS
            0xc4 -> {
                return context.getString(R.string.sos_static)
            }
            //运动、SOS
            0xc6 -> {
                return context.getString(R.string.sos_sport)
            }
        }
        return context.getString(R.string.still)
    }
}


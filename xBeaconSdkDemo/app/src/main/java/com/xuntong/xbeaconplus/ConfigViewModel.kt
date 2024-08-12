package com.xuntong.xbeaconplus

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ConfigViewModel(application: Application) : AndroidViewModel(application) {

    var valueUuid = ""
    var valueMajor = 0
    var valueMinor = 0
    var valueGap = 0
    var valueTxPower = 0
    var valueMRSSI = 0
    var valueSlowGap = 0
    var valueInSleepTime = 0
    var version = ""

    var broadcastIntervalList = ArrayList<String>()
    var transmitPowerList = ArrayList<String>()
    var mRSSIList = ArrayList<String>()

    init {
        broadcastIntervalData()
        transmitPowerData()
        rssiData()
    }

    fun broadcastIntervalData(): ArrayList<String> {
        for (i in 1..50) {
            var time = i * 100
            broadcastIntervalList.add(time.toString())
        }

        return broadcastIntervalList
    }

    fun getBroadcastIntervalLastIndex(): Int {
        var time = valueGap.toString()
        var index = 0
        for (i in broadcastIntervalList.indices) {
            if (time == broadcastIntervalList[i]) {
                index = i
                return index
            }
        }
        return index
    }

    fun getSlowBroadcastIntervalLastIndex(): Int {
        var time = valueSlowGap.toString()
        var index = 0
        for (i in broadcastIntervalList.indices) {
            if (time == broadcastIntervalList[i]) {
                index = i
                return index
            }
        }
        return index
    }

    fun rssiData(): ArrayList<String> {
        for (i in -127..20) {
            mRSSIList.add(i.toString())
        }

        return mRSSIList
    }

    fun getRssiLastIndex(): Int {
        var rssi = valueMRSSI.toString()
        var index = 0
        for (i in mRSSIList.indices) {
            if (rssi == mRSSIList[i]) {
                index = i
                return index
            }
        }
        return index
    }

    fun transmitPowerData(): ArrayList<String> {
        transmitPowerList.add("-40")
        var startNum = -20
        while (startNum < 5) {
            transmitPowerList.add(startNum.toString())
            startNum += 4
        }

        return transmitPowerList
    }

    fun getTransmitPowerLastIndex(): Int {
        var time = powerIndexToValue(valueTxPower).toString()
        var index = 0
        for (i in transmitPowerList.indices) {
            if (time == transmitPowerList[i]) {
                index = i
                return index
            }
        }
        return index
    }

    /**
     * 将界面值转换成发射功率
     *
     * @param value
     * @return
     */
    fun powerValueToIndex(value: Int): Int {
        var power = 0
        when (value) {
            4 -> power = 0
            0 -> power = 1
            -4 -> power = 2
            -8 -> power = 3
            -12 -> power = 4
            -16 -> power = 5
            -20 -> power = 6
            -40 -> power = 7
        }
        return power
    }

    /**
     * 将发射功率转换为界面值
     *
     * @param power
     * @return
     */
    fun powerIndexToValue(index: Int): Int {
        var value = 4
        when (index) {
            0 -> value = 4
            1 -> value = 0
            2 -> value = -4
            3 -> value = -8
            4 -> value = -12
            5 -> value = -16
            6 -> value = -20
            7 -> value = -40
        }
        return value
    }
}
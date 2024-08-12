package com.xuntong.xbeaconplus

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.kongzue.dialogx.dialogs.WaitDialog
import com.xuntong.xbeaconplus.databinding.ActivityMainBinding
import com.xuntong.xbeaconsdk.open.ScanedBluetoothDevice
import com.xuntong.xbeaconsdk.open.XtManager
import com.xuntong.xbeaconsdk.open.XtScanFilter

class MainActivity : AppCompatActivity(), XtManager.BleStateListener {

    lateinit var binding: ActivityMainBinding
    private var scanAdapter: ScanListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.scanTitleView.centerTitleTv.text = getString(R.string.dev_scan)

        binding.circleAnimationView.setDuration(6000)
        binding.circleAnimationView.setStyle(Paint.Style.FILL)
        binding.circleAnimationView.setColor(getColor(R.color.colorPrimaryQian))
        binding.circleAnimationView.setInterpolator(LinearOutSlowInInterpolator())
        binding.circleAnimationView.start()

        initManager()
        initScanDev()
    }

    override fun onResume() {
        super.onResume()
        XtManager.getInstance().startScan()
        if(XtManager.getInstance().isConnected()){
            XtManager.getInstance().disconnect()
        }
    }

    override fun onStop() {
        super.onStop()
        XtManager.getInstance().stopScan()
        scanAdapter?.setNewData(null)
    }

    private fun initManager() {
        XtManager.initialize(this)
    }

    private fun initScanDev() {
        //scan filter
        var xtScanFilter = XtScanFilter
            .setFilterRssi(-55)
            .enableFilterDevice(true)
//            .setFilterDevType(DeviceType.CARD_TYPE_KC_B1_HX)
//            .setFilterName("AABB")

        //Set up scan filters
        XtManager.getInstance().setScanFilters(xtScanFilter)

        //start scanning
        XtManager.getInstance().startScan()

        //scan result monitoring
        XtManager.getInstance().setBleScanListener(object : XtManager.BleScanResultListener {
            override fun onScanResult(scanedBluetoothDevice: MutableList<ScanedBluetoothDevice>) {
                //To process the scan result, the device that needs to be scanned will call the connection interface to connect
                scanAdapter = ScanListAdapter(null)
                val linearLayoutManager = LinearLayoutManager(this@MainActivity)
                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                binding.recyclerViewDevs.layoutManager = linearLayoutManager
                binding.recyclerViewDevs.adapter = scanAdapter
                scanAdapter!!.addChildClickViewIds(R.id.connectBt)
                scanAdapter!!.setOnItemChildClickListener(onItemChildClick)
                scanAdapter!!.setNewData(scanedBluetoothDevice)
            }
        })

        //Monitor Bluetooth status
        XtManager.getInstance().setBleStateListener(this@MainActivity)
    }

    var handler : Handler?=null
    val onItemChildClick = OnItemChildClickListener { adapter, view, position ->
        if (view.id == R.id.connectBt) {
            WaitDialog.show(getString(R.string.connecting))
            XtManager.getInstance().connectInitPsd("123456",true)
            XtManager.getInstance().connect(adapter.getItem(position) as ScanedBluetoothDevice)

            handler = Handler()
            handler?.postDelayed(Runnable {
                WaitDialog.dismiss()
                ToastUtils.showLong(getString(R.string.connecttimeout))
            }, 15000)
        }
    }

    override fun onDisConnected() {

    }

    override fun onConnected() {

    }

    /**
     * The connection is successful and the password is correct
     */
    override fun onReady() {
        handler?.removeCallbacksAndMessages(null)
        WaitDialog.dismiss()
        ActivityUtils.startActivity(ConfigActivity::class.java)
    }
}
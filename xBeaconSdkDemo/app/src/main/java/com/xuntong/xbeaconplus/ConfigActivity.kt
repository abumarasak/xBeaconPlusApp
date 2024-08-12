package com.xuntong.xbeaconplus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.xuntong.xbeaconplus.databinding.ActivityConfigBinding
import com.xuntong.xbeaconplus.intf.PickerViewSelectChangeListener
import com.xuntong.xbeaconsdk.open.XtManager

class ConfigActivity : AppCompatActivity(), XtManager.BleDataReceiveListener, View.OnClickListener {

    lateinit var binding:ActivityConfigBinding
    lateinit var viewModel:ConfigViewModel
    private var mUUID: String? = null
    private var mPower = 0;
    private var mInterval = 0;
    private var mMajor = 0;
    private var mMinor = 0;
    private var mRssi = 0;
    private var mBattery = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ConfigViewModel::class.java]

        binding.subTool.leftTitleTv.visibility = View.VISIBLE
        binding.subTool.leftTitleTv.setOnClickListener {
            finish()
        }
        binding.subTool.centerTitleTv.text = getString(R.string.dev_config)

        XtManager.getInstance().readData(XtManager.getInstance().OPCODE_ALL)

        XtManager.getInstance().setBleDataReceiveListener(this)
        XtManager.getInstance().setBleResultListener(bleResultListener)


        binding.dtView.clickLayoutUuid.setOnClickListener(this)
        binding.dtView.clickLayoutMajor.setOnClickListener(this)
        binding.dtView.clickLayoutMinor.setOnClickListener(this)
        binding.dtView.clickLayoutGap.setOnClickListener(this)
        binding.dtView.clickLayoutTxPower.setOnClickListener(this)
        binding.dtView.clickLayoutMRSSI.setOnClickListener(this)
        binding.clickLayoutPsd.setOnClickListener(this)
    }

    var bleResultListener = object : XtManager.BleResultListener{
        override fun onConfigMajorResult(isSuccess: Boolean) {
            if(isSuccess){
                ToastUtils.showLong(getString(R.string.config_success))
            }else{
                ToastUtils.showLong(getString(R.string.config_fail))
            }
        }

        override fun onConfigMinorResult(isSuccess: Boolean) {
            if(isSuccess){
                ToastUtils.showLong(getString(R.string.config_success))
            }else{
                ToastUtils.showLong(getString(R.string.config_fail))
            }
        }

        override fun onConfigUuidResult(isSuccess: Boolean) {
            if(isSuccess){
                ToastUtils.showLong(getString(R.string.config_success))
            }else{
                ToastUtils.showLong(getString(R.string.config_fail))
            }
        }

        override fun onConfigGapResult(isSuccess: Boolean) {
            if(isSuccess){
                ToastUtils.showLong(getString(R.string.config_success))
            }else{
                ToastUtils.showLong(getString(R.string.config_fail))
            }
        }

        override fun onConfigPowerResult(isSuccess: Boolean) {
            if(isSuccess){
                ToastUtils.showLong(getString(R.string.config_success))
            }else{
                ToastUtils.showLong(getString(R.string.config_fail))
            }
        }

        override fun onConfigMRssiResult(isSuccess: Boolean) {
            if(isSuccess){
                ToastUtils.showLong(getString(R.string.config_success))
            }else{
                ToastUtils.showLong(getString(R.string.config_fail))
            }
        }

        override fun onConfigPsd(isSuccess: Boolean) {
            if(isSuccess){
                ToastUtils.showLong(getString(R.string.config_success))
            }else{
                ToastUtils.showLong(getString(R.string.config_fail))
            }
        }

    }

    override fun receiveData(opcode: Int, data: Int?) {
        when(opcode){
            XtManager.getInstance().OPCODE_MAJOR->{
                binding.dtView.tvMajor.text = data.toString()
            }
            XtManager.getInstance().OPCODE_MINOR->{
                binding.dtView.tvMinor.text = data.toString()
            }
            XtManager.getInstance().OPCODE_GAP->{
                binding.dtView.tvGap.text = data.toString()
            }
            XtManager.getInstance().OPCODE_MRSSI->{
                binding.dtView.tvMRSSI.text = data.toString()
            }
            XtManager.getInstance().OPCODE_POWER->{
                binding.dtView.tvTxPower.text = data.toString()
            }
            XtManager.getInstance().OPCODE_BATTERY->{
                binding.dtView.tvBattery.text = data.toString()
            }
        }
    }

    override fun receiveData(opcode: Int, data: String?) {
        when(opcode){
            XtManager.getInstance().OPCODE_UUID->{
                binding.dtView.tvUUID.text = data.toString()
            }
        }
    }

    override fun onClick(v: View?) {
        when(v){
            binding.dtView.clickLayoutUuid -> {
                showUUIDConfig()
            }
            binding.dtView.clickLayoutMajor -> {
                showMajorConfig()
            }
            binding.dtView.clickLayoutMinor -> {
                shoMinorConfig()
            }
            binding.dtView.clickLayoutGap -> {
                showBroadcastInterval()
            }
            binding.dtView.clickLayoutTxPower -> {
                showTxPower()
            }
            binding.dtView.clickLayoutMRSSI -> {
                showMRSSI()
            }
            binding.clickLayoutPsd -> {
                showPsdConfig()
            }
        }
    }

    private fun showMRSSI() {
        PickerUtils.showCustomOptionPicker(
            this, getString(R.string.config_m_rssi), "",
            viewModel.mRSSIList,
            object : PickerViewSelectChangeListener {
                override fun selectChange(selectItem: String?) {
                    binding.dtView.tvMRSSI.text = selectItem + "dbm"
                    viewModel.valueMRSSI = selectItem!!.toInt()
                    XtManager.getInstance().setMRssi(selectItem!!.toInt())
                }
            }, viewModel.getRssiLastIndex(),"dbm"
        )
    }

    private fun showPsdConfig(){
        XtManager.getInstance().configPsd("123456")
    }

    private fun showUUIDConfig(){
        XtDialogUtils.showEditDialog(getString(R.string.config_uuid),"",getString(R.string.sure),
            getString(R.string.cancel),viewModel.valueUuid,false,getString(R.string.tip_hint_uuid)
        ) { text ->
            if(text.length!=36){
                ToastUtils.showLong(getString(R.string.uuid_format_error))
            }else{
                XtDialogUtils.hideInputDialog()
                viewModel.valueUuid = text
                binding.dtView.tvUUID.text = viewModel.valueUuid
                XtManager.getInstance().setUUID(viewModel.valueUuid)
            }
        }
    }

    private fun showMajorConfig(){
        XtDialogUtils.showEditDialog(getString(R.string.config_major),"",getString(R.string.sure),
            getString(R.string.cancel),viewModel.valueMajor.toString(),true,getString(R.string.minor_hint)
        ) { text ->
            if(text.toInt()>65535 || text.toInt()<0){
                ToastUtils.showLong(getString(R.string.input_error_tip_normal))
            }else{
                XtDialogUtils.hideInputDialog()
                viewModel.valueMajor = text.toInt()
                binding.dtView.tvMajor.text = viewModel.valueMajor.toString()
                XtManager.getInstance().setMajor(viewModel.valueMajor)
            }
        }
    }

    private fun shoMinorConfig(){
        XtDialogUtils.showEditDialog(getString(R.string.config_minor),"",getString(R.string.sure),
            getString(R.string.cancel),viewModel.valueMinor.toString(),true,getString(R.string.minor_hint)
        ) { text ->
            if(text.toInt()>65535 || text.toInt()<0){
                ToastUtils.showLong(getString(R.string.input_error_tip_normal))
            }else{
                XtDialogUtils.hideInputDialog()
                viewModel.valueMinor = text.toInt()
                binding.dtView.tvMinor.text = viewModel.valueMinor.toString()
                XtManager.getInstance().setMinor(viewModel.valueMinor)
            }
        }
    }

    fun showBroadcastInterval() {
        PickerUtils.showCustomOptionPicker(
            this, getString(R.string.config_gap), "",
            viewModel.broadcastIntervalList,
            object :PickerViewSelectChangeListener{
                override fun selectChange(selectItem: String?) {
                    binding.dtView.tvGap.text = selectItem + "ms"
                    viewModel.valueGap = selectItem!!.toInt()
                    XtManager.getInstance().setGap(viewModel.valueGap)
                }
            }, viewModel.getBroadcastIntervalLastIndex(),"ms"
        )
    }

    fun showTxPower() {
        PickerUtils.showCustomOptionPicker(
            this, getString(R.string.config_power), "",
            viewModel.transmitPowerList,
            object :PickerViewSelectChangeListener{
                override fun selectChange(selectItem: String?) {
                    binding.dtView.tvTxPower.text = selectItem + "db"
                    viewModel.valueTxPower = selectItem!!.toInt()
                    XtManager.getInstance().setPower(viewModel.valueTxPower)
                }
            }, viewModel.getTransmitPowerLastIndex(),"db"
        )
    }
}
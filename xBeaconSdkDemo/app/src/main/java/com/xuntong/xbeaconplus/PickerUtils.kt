package com.xuntong.xbeaconplus

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.view.OptionsPickerView
import com.bigkoo.pickerview.view.TimePickerView
import com.xuntong.xbeaconplus.R
import com.xuntong.xbeaconplus.intf.PickerViewSelectChangeListener
import com.xuntong.xbeaconplus.intf.PickerViewSelectIndexChangeListener
import com.xuntong.xbeaconplus.intf.PickerViewSelectTimeChangeListener
import com.xuntong.xbeaconplus.intf.PickerViewTwoSelectChangeListener
import java.util.*

object PickerUtils {

    @SuppressLint("StaticFieldLeak")
    lateinit var pvCustomOptions: OptionsPickerView<String>
    lateinit var pvCustomTime: TimePickerView

    fun showCustomOptionPicker(
        context: Context, title: String, subTitle: String,
        selectLs: MutableList<String>,
        pickerViewSelectChangeListener: PickerViewSelectChangeListener,
        currentCheck: Int,label:String = ""
    ) {
        pvCustomOptions = OptionsPickerBuilder(context
        ) { options1, options2, options3, v ->
            pickerViewSelectChangeListener.selectChange(selectLs[options1])
        }
            .setLayoutRes(
                R.layout.pickerview_custom_options
            ) { v ->
                val tvSubmit = v?.findViewById<TextView>(R.id.tv_finish)
                val ivCancel = v?.findViewById<TextView>(R.id.iv_cancel)
                val tvTitle = v?.findViewById<TextView>(R.id.tvTitle)
                val tvSubTitle = v?.findViewById<TextView>(R.id.tvSubTitle)
                tvTitle?.text = title
                tvSubTitle?.text = subTitle
                tvSubmit?.setOnClickListener {
                    pvCustomOptions.returnData()
                    pvCustomOptions.dismiss()
                }
                ivCancel?.setOnClickListener {
                    pvCustomOptions.dismiss()
                }
            }
            .setLabels(label,label,label)
            .isCenterLabel(true)
            .isDialog(false)
            .setOutSideCancelable(false)
            .build()
        pvCustomOptions.setPicker(selectLs) //添加数据
        pvCustomOptions.setSelectOptions(currentCheck)
        pvCustomOptions.show()
    }

    fun showCustomOptionIndexPicker(
        context: Context, title: String, subTitle: String,
        selectLs: MutableList<String>,
        pickerViewSelectChangeListener: PickerViewSelectIndexChangeListener,
        currentCheck: Int
    ) {
        pvCustomOptions = OptionsPickerBuilder(context
        ) { options1, options2, options3, v ->
            pickerViewSelectChangeListener.selectChange(options1)
        }
            .setLayoutRes(
                R.layout.pickerview_custom_options
            ) { v ->
                val tvSubmit = v?.findViewById<TextView>(R.id.tv_finish)
                val ivCancel = v?.findViewById<TextView>(R.id.iv_cancel)
                val tvTitle = v?.findViewById<TextView>(R.id.tvTitle)
                val tvSubTitle = v?.findViewById<TextView>(R.id.tvSubTitle)
                tvTitle?.text = title
                tvSubTitle?.text = subTitle
                tvSubmit?.setOnClickListener {
                    pvCustomOptions.returnData()
                    pvCustomOptions.dismiss()
                }
                ivCancel?.setOnClickListener {
                    pvCustomOptions.dismiss()
                }
            }
            .isCenterLabel(true)
            .isDialog(false)
            .setOutSideCancelable(false)
            .build()
        pvCustomOptions.setPicker(selectLs) //添加数据
        pvCustomOptions.setSelectOptions(currentCheck)
        pvCustomOptions.show()
    }

    fun showCustomTwoOptionPicker(
        context: Context, title: String, subTitle: String,
        selectLs1: MutableList<String>,
        selectLs2: MutableList<String>,
        pickerViewSelectChangeListener: PickerViewTwoSelectChangeListener,
        currentCheck1: Int,
        currentCheck2: Int
    ) {
        pvCustomOptions = OptionsPickerBuilder(context
        ) { options1, options2, options3, v ->
            pickerViewSelectChangeListener.selectChange(selectLs1[options1],selectLs2[options2])
        }
            .setLayoutRes(R.layout.pickerview_custom_options
            ) { v ->
                val tvSubmit = v?.findViewById<TextView>(R.id.tv_finish)
                val ivCancel = v?.findViewById<TextView>(R.id.iv_cancel)
                val tvTitle = v?.findViewById<TextView>(R.id.tvTitle)
                val tvSubTitle = v?.findViewById<TextView>(R.id.tvSubTitle)
                tvTitle?.text = title
                tvSubTitle?.text = subTitle
                tvSubmit?.setOnClickListener {
                    pvCustomOptions.returnData()
                    pvCustomOptions.dismiss()
                }
                ivCancel?.setOnClickListener {
                    pvCustomOptions.dismiss()
                }
            }
            .isCenterLabel(true)
            .isDialog(false)
            .setOutSideCancelable(false)
            .build()
        pvCustomOptions.setNPicker(selectLs1,selectLs2,null) //添加数据
        pvCustomOptions.setSelectOptions(currentCheck1,currentCheck2)
        pvCustomOptions.show()
    }
}
package com.yuk.miuihome.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import com.yuk.miuihome.HomeContext
import com.yuk.miuihome.R
import com.yuk.miuihome.utils.*
import com.yuk.miuihome.utils.ownSP

@SuppressLint("ViewConstructor", "SetTextI18n")
class SettingSeekBar(
    context: Context,
    private val mText: String,
    private val mKey: String,
    private val minValue: Int,
    private val maxValue: Int,
    private val divide: Int = 10,
    private val defValue: Int,
    private val canUserInput: Boolean
) :
    LinearLayout(context) {
    var text: String = ""
        set(value) {
            field = value
            textView.text = value
        }

    private val seekBar: SeekBar
    private val textView: TextView
    private val editor by lazy { ownSP.edit() }
    private val myRes by lazy { HomeContext.resInstance.moduleRes.resources }
    lateinit var valueTextView: TextView
    var tempValue: Float = ownSP.getFloat(mKey, 0f)
    var key = ""

    init {
        orientation = VERTICAL
        setPadding(
            dip2px(10),
            dip2px(7),
            dip2px(10),
            dip2px(7)
        )
        textView = TextView(context)
        seekBar = SeekBar(HomeContext.context).apply {
            min = minValue
            max = maxValue
            progress = (tempValue * divide).toInt()
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    saveValue(progress.toFloat() / divide)
                    valueTextView.text = "$progress"
                    tempValue = (progress.toFloat() / divide)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
        }
        addView(LinearLayout(HomeContext.context).apply {
            addView(textView.apply {
                layoutParams =
                    LayoutParams(350, LayoutParams.MATCH_PARENT)
                setTextColor(Color.parseColor(if (isNightMode(context)) "#ffffff" else "#000000"))
            })
            addView(TextView(HomeContext.context).apply {
                text = ""
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                layoutParams =
                    LayoutParams(0, LayoutParams.MATCH_PARENT, 1f)
                setTextColor(Color.parseColor(if (isNightMode(context)) "#ffffff" else "#000000"))
            })
            addView(TextView(HomeContext.context).apply {
                text = myRes.getString(R.string.Defaults) + " : $defValue"
                textAlignment = TextView.TEXT_ALIGNMENT_TEXT_END
                layoutParams =
                    LayoutParams(300, LayoutParams.MATCH_PARENT)
                setTextColor(Color.parseColor(if (isNightMode(context)) "#ffffff" else "#000000"))
            })
            gravity = Gravity.CENTER_VERTICAL
        })
        addView(seekBar)
        addView(LinearLayout(HomeContext.context).apply {
            addView(TextView(HomeContext.context).apply {
                text = "$minValue"
                layoutParams =
                    LayoutParams(150, LayoutParams.MATCH_PARENT)
                setTextColor(Color.parseColor(if (isNightMode(context)) "#ffffff" else "#000000"))
            })
            addView(TextView(HomeContext.context).apply {
                text = "${(tempValue * divide).toInt()}"
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                valueTextView = this
                layoutParams =
                    LayoutParams(0, LayoutParams.MATCH_PARENT, 1f)
                setTextColor(Color.parseColor(if (isNightMode(context)) "#ffffff" else "#000000"))
            })
            addView(TextView(HomeContext.context).apply {
                text = "$maxValue"
                textAlignment = TextView.TEXT_ALIGNMENT_TEXT_END
                layoutParams =
                    LayoutParams(150, LayoutParams.MATCH_PARENT)
                setTextColor(Color.parseColor(if (isNightMode(context)) "#ffffff" else "#000000"))
            })
            gravity = Gravity.CENTER_VERTICAL
        })
        if (canUserInput) {
            addView(
                SettingTextView.FastBuilder(
                    mText = myRes.getString(R.string.ManualInput)
                ) {
                    SettingUserInputNumber(
                        mText,
                        mKey,
                        minValue,
                        maxValue,
                        divide,
                        defValue
                    ).build()
                }.build()
            )
        }
    }

    class FastBuilder(
        private val mContext: Context = HomeContext.context,
        private val mText: String,
        private val mKey: String,
        private val minValue: Int,
        private val maxValue: Int,
        private val divide: Int = 10,
        private val defValue: Int,
        private val canUserInput: Boolean = false
    ) {
        fun build() = SettingSeekBar(
            mContext,
            mText,
            mKey,
            minValue,
            maxValue,
            divide,
            defValue,
            canUserInput
        ).apply {
            text = mText
            key = mKey
            minValue
            maxValue
            divide
            defValue
            canUserInput
        }
    }

    fun saveValue(value: Float): Boolean {
        if ((value < (minValue.toFloat() / divide)) or (value > (maxValue.toFloat() / divide))) {
            LogUtil.toast(myRes.getString(R.string.OutOfInput))
            return false
        }
        editor.putFloat(mKey, value)
        editor.apply()
        return true
    }
}
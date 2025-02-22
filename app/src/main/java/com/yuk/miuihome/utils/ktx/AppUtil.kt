package com.yuk.miuihome.utils.ktx

import android.content.Context
import android.content.res.Configuration
import com.yuk.miuihome.utils.HomeContext

fun dp2px(context: Context, dpValue: Float): Int = (dpValue * context.resources.displayMetrics.density + 0.5f).toInt()

fun dp2px(dpValue: Float): Int = (dpValue * HomeContext.context.resources.displayMetrics.density + 0.5f).toInt()

fun dip2px(dpValue: Int): Int = (dpValue * HomeContext.context.resources.displayMetrics.density + 0.5f).toInt()

fun sp2px(context: Context, spValue: Float): Float = (spValue * context.resources.displayMetrics.scaledDensity + 0.5f)

fun sp2px(spValue: Float): Float = (spValue * HomeContext.context.resources.displayMetrics.scaledDensity + 0.5f)

fun px2dip(pxValue: Int): Int = (pxValue / HomeContext.context.resources.displayMetrics.density + 0.5f).toInt()
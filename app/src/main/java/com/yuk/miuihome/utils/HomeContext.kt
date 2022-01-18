package com.yuk.miuihome.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context

@SuppressLint("StaticFieldLeak")
object HomeContext {
    lateinit var application: Application
    lateinit var context: Context
    lateinit var classLoader: ClassLoader
    lateinit var settingActivity: Activity
}
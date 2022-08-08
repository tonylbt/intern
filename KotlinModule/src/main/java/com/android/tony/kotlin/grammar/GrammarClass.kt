package com.android.tony.kotlin.grammar

import android.text.TextUtils
import android.util.Log
import com.android.tony.kotlin.grammar.bean.DailyBean
import java.time.DayOfWeek

class GrammarClass {
    private val TAG = "libt.GrammarClass"
    private var name : String = "GrammarClass"
    private var packageName : String

    public var address : String = "zhong_guan_cun_ruan_jian_yuan_23"
    set(value) {
        if (!TextUtils.isEmpty(value)) {
            field = value
        }
    }

    public lateinit var postcode : String
    public lateinit var email : String

    constructor(pkg : String) {
        packageName = pkg
    }

    constructor(nm : String, pkg : String) : this(pkg) {
        name = nm
    }

    private fun clazzInfo() {
        Log.d(TAG, "This is $name fun clazzInfo().")
    }

    lateinit var day : DailyBean
    public fun main() {
        day = DailyBean("Friday")
    }

    fun exec() {
        Log.d(TAG, "The day is${day.getDay()}")
    }
}
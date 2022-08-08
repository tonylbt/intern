package com.android.tony.kotlin.grammar.bean

open class DailyBean {
    private lateinit var mID : String

    constructor(id : String) {
        mID = id
    }

    fun getDay() : String {
        return mID
    }
}
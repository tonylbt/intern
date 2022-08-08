package com.android.tony.kotlin.grammar.bean

import android.text.TextUtils

open abstract class BaseBean() {
    protected val TAG = "libt." + javaClass.simpleName

    private var mID : String = "" + System.currentTimeMillis()
    get() = field
    set(value) {
        if (!TextUtils.isEmpty(field)) {
            field = value
        }
    }

    constructor(id: String) : this(){
        mID = id
    }

    abstract fun main()
}
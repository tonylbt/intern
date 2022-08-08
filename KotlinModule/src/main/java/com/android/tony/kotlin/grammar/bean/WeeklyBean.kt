package com.android.tony.kotlin.grammar.bean

import android.text.TextUtils
import android.util.Log
import kotlin.random.Random

class WeeklyBean constructor(day : String) : BaseBean() {
    private val strArray = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    public var dayName : String = day

    public var dayIndex : Int = 1
    get() = field
    set(value) {
        if (value in 0..7) {
            field = value
        } else {
            field = 1
        }
    }

    constructor(name: String, idx: Int) : this(name) {
        dayName = name
        dayIndex = idx
    }

    override fun main() {
        Log.d(TAG, "fun main return value=${getToday()}")

        var weekend = Weekend()
        Log.d(TAG, "weekend day=${weekend.getWeekend()}")

        var saturday = Saturday()
        saturday.getDay()
    }

    public fun getToday() : String {
        if (TextUtils.isEmpty(dayName)) {
            dayIndex = Random(7).nextInt()
            dayName = strArray[dayIndex]
        } else {
            parseIndexByName(dayName)
        }
        Log.d(TAG, "WeeklyBean::: Today is the $dayIndex day in a week, name is $dayName")
        return dayName
    }

    private fun parseIndexByName(dayName : String) {
        for (idx in strArray.indices) {
            if (TextUtils.equals(strArray[idx], dayName)) {
                dayIndex = idx + 1
                break
            }
        }
    }

    class Weekend {

        public fun getWeekend() : String {
            return "Our weekend is " + "Saturday and Sunday!"
        }
    }

    inner class Saturday {
        fun getDay() {
            Log.d(TAG, this@WeeklyBean.getToday() + ", Saturday::: This is Saturday.")

            dayName = "Saturday"
            dayIndex = 6
            Log.d(TAG, "Saturday:::reset day value ")
            Log.d(TAG, this@WeeklyBean.getToday() + ", This is Saturday.")
        }
    }

}
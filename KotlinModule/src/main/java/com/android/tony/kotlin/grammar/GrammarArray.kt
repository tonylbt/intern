package com.android.tony.kotlin.grammar

import com.android.tony.kotlin.grammar.bean.WeeklyBean

class GrammarArray {
    private val intArray = arrayOf(29, 1, 9, 2664, 820)
    private val strArray = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    private val charArray = charArrayOf('D', 'e', 'c', 'e', 'm', 'b', 'e', 'r')
    private val TYPE : Int = 1
    private lateinit var mWeekly : WeeklyBean

    public fun mainMethod() {
        traverse1()

        traverse2(23, 34, 97, 967, 7889)



    }


    /**======================================== fun ===========================================**/


    private fun traverse1() {
        println("exec fun traverse1........\n")
        for(idx in intArray.indices) {
            println("index $idx in intArray is ${intArray[idx]}")
        }
    }

    private fun traverse2(vararg args : Int) : Unit {
        var arg : Int
        println("traverse2 mode1::::\n")
        val size : Int = args.size - 1
        for(i in 0..size step 1) {
            arg = args[i]
            print("arg$i=$arg;")
        }

        println("\ntraverse2 mode2::::\n")
        for(i in 0 until args.size step 1) {
            arg = args[i]
            print("arg$i=$arg;")
        }

        println("\ntraverse2 mode3::::\n")
        for(idx in args.indices) {
            println("index $idx in args is ${args[idx]}")
        }
    }


}
package com.android.tony.kotlin.grammar

class GrammarBasics {
    private lateinit var mUpdateListener : UpdateListener

    public fun mainEntrance() {
        var str = "fun sum() result="
        printAndNotifyResult(str)
        str = "" + sum(5, 29)
        printAndNotifyResult(str)
        printLine()

        str = "fun multi() result="
        printAndNotifyResult(str)
        str = "" + multi(2, 23)
        printAndNotifyResult(str)
        printLine()

        str = "multiParams()=" + multiParams(6, 7, 98, 579)
        printAndNotifyResult(str)
    }

    private fun printLine() {
        printAndNotifyResult("\n\n")
    }

    private fun sum(a : Int, b : Int) : Int {
        print(a)
        print(b)
        return a + b
    }

    private fun multi(a : Int, b : Int) = a * b

    private fun multiParams(vararg args : Int) : String {
        var str = "{"
        var arg : Int
//        val size : Int = args.size - 1
//        for(i in 0..size step 1) {
        for(i in 0 until args.size - 1 step 1) {
            arg = args[i]
            str += "arg$i=$arg;"
        }

//        for (arg in args) {
//            str += "arg=$arg;"
//        }
        str += "}"
        return str
    }


    //============================================================================================
    //============================================================================================
    private fun printAndNotifyResult(string: String) {
        print(string)
        // TODO "?."
        mUpdateListener?.onUpdateContent(string)
    }

    public fun setUpdateListener(listener: UpdateListener) {
        mUpdateListener = listener
    }

    public interface UpdateListener {
        fun onUpdateContent(content : String)
    }

}
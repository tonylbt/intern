package com.android.tony.kotlin.utils

import android.content.Context
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Looper
import android.util.Log
import java.lang.reflect.Method
import java.util.HashMap

class HotspotManager {
    private val TAG = "HotspotMgr"

    val WIFI_AP_STATE_DISABLING = 10
    val WIFI_AP_STATE_DISABLED = 11
    val WIFI_AP_STATE_ENABLING = 12
    val WIFI_AP_STATE_ENABLED = 13
    val WIFI_AP_STATE_FAILED = 14
    val WIFI_AP_STATE_UNKNOWN = WifiManager.WIFI_STATE_UNKNOWN

    private val METHOD_GET_WIFI_AP_STATE = "getWifiApState"
    private val METHOD_SET_WIFI_AP_ENABLED = "setWifiApEnabled"
    private val METHOD_GET_WIFI_AP_CONFIG = "getWifiApConfiguration"
    private val METHOD_IS_WIFI_AP_ENABLED = "isWifiApEnabled"
    private val METHOD_SET_WIFI_AP_CONFIG = "setWifiApConfiguration"
    private val METHOD_SET_WIFI_AP_CONFIG_EX = "setWifiApConfig"

    private lateinit var mWifiManager: WifiManager

    private val mMethods = HashMap<String, Method>()

    fun closeWifi(context: Context) {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        setWifiEnabledEx(wifiManager, null, false)
    }

    // WifiMaster call the method, ex_version support more reasonable logic.
    fun setWifiEnabledEx(wifiManager: WifiManager, hotspotManager: HotspotManager?, enabled: Boolean): Boolean {
        if (enabled && hotspotManager != null && hotspotManager.isHotspotEnabled())
            hotspotManager.enableHotspot(null, false)

        var result = true
        if (enabled xor wifiManager.isWifiEnabled) {
            // 实际测试中，发现Lenovo A630在调用setWifiEnabled()会抛出空指针的异常，
            // 从而引起不能打开AP的情况。
            try {
                result = wifiManager.setWifiEnabled(enabled)
            } catch (e: Exception) {
                Log.w(TAG, e)
            }

        }
        Log.v(TAG, "enableWifi(%b) result = %b", enabled, result)
        return result
    }

    fun isHotspotEnabled(): Boolean {
        var result = false
        try {
            val method = mMethods.get(METHOD_IS_WIFI_AP_ENABLED)
            result = method!!.invoke(mWifiManager)
        } catch (e: Exception) {
            Log.w(TAG, e)
        }

        return result
    }

    // Note: please make sure caller already called WifiManager.setWifiEnabled(false) before call enableHotspot(true)
    @Synchronized
    fun enableHotspot(config: WifiConfiguration, enable: Boolean): Boolean {
        val startTime = System.currentTimeMillis()

        var result = false
        var retryCount = 0
        var errorMsg = "noerror"
        try {
            val method = mMethods.get(METHOD_SET_WIFI_AP_ENABLED)
            while (true) {
                try {
                    result = method!!.invoke(mWifiManager, config, enable)
                } catch (e: Exception) {
                    errorMsg = e.message
                    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1)
                        break
                }

                if (result)
                    break

                retryCount++
                if (retryCount == 3)
                    break

                Thread.sleep(2000)
            }
        } catch (e: Exception) {
            errorMsg = e.message
            Log.w(TAG, e)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !enable && result)
            waitApDisabled()

        Log.d(TAG, "enableHotspot enable : " + enable + " end duration = " + (System.currentTimeMillis() - startTime))

        return result
    }

    private fun waitApDisabled() {
        // Don't block main thread
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.e(TAG, "waiApDisabled should not run on UI thread.", Exception())
            return
        }

        val AP_STOP_CHECK_STEP: Long = 100
        var leftCheckTime: Long = 8000
        while (leftCheckTime > 0) {
            val state = getWifiApState()
            if (state == WIFI_AP_STATE_DISABLED || state == WIFI_AP_STATE_FAILED)
                break

            try {
                Thread.sleep(AP_STOP_CHECK_STEP)
                leftCheckTime -= AP_STOP_CHECK_STEP
            } catch (e: InterruptedException) {
            }

        }

        if (leftCheckTime == 0L)
            Log.w(TAG, "waitApDisabled timeout, ap not disabled complete")
    }

    fun getWifiApState(): Int {
        try {
            val method = mMethods.get(METHOD_GET_WIFI_AP_STATE)
            return method!!.invoke(mWifiManager)
        } catch (e: Exception) {
            Log.w(TAG, e)
        }

        return WIFI_AP_STATE_UNKNOWN
    }

}
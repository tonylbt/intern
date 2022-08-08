package com.android.tony.kotlin.ui.activity

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

import com.android.tony.kotlin.R

class HotspotActivityJ : AppCompatActivity() {
    private var btnDataConn: Button? = null
    private var btnHotspot: Button? = null
    private var wifiManager: WifiManager? = null

    private val onClickListener = View.OnClickListener { view ->
        if (view.id == R.id.btn_data) {
            openDataConnection()
        } else if (view.id == R.id.btn_hotspot) {
            openHotspot(false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_hotspot_layout)
        btnDataConn = findViewById(R.id.btn_data)
        btnHotspot = findViewById(R.id.btn_hotspot)
        btnDataConn!!.setOnClickListener(onClickListener)
        btnHotspot!!.setOnClickListener(onClickListener)

        //获取wifi管理服务
        wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager
    }

    private fun openDataConnection() {

    }

    private fun openHotspot(enabled: Boolean): Boolean {
        if (enabled) { // disable WiFi in any case
            //wifi和热点不能同时打开，所以打开热点的时候需要关闭wifi
            wifiManager!!.isWifiEnabled = false
        }

        try {
            //热点的配置类
            val apConfig = WifiConfiguration()
            //配置热点的名称(可以在名字后面加点随机数什么的)
            apConfig.SSID = "YRCCONNECTION"
            //配置热点的密码
            apConfig.preSharedKey = "12122112"
            //通过反射调用设置热点
            val method = wifiManager!!.javaClass.getMethod(
                    "setWifiApEnabled", WifiConfiguration::class.java, java.lang.Boolean.TYPE)
            //返回热点打开状态
            return method.invoke(wifiManager, apConfig, enabled) as Boolean
        } catch (e: Exception) {
            return false
        }

    }

    companion object {

        fun openHotspotActivity(context: Context) {
            val intent = Intent(context, HotspotActivityJ::class.java)
            context.startActivity(intent)
        }
    }
}

package com.android.tony.kotlin.ui.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.provider.Settings
import android.util.Pair
import android.view.View.OnClickListener
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.android.tony.kotlin.R
import com.google.android.material.snackbar.Snackbar

class HotspotActivity : AppCompatActivity() {
    private lateinit var btnDataConn: Button
    private lateinit var btnHotspot: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_hotspot_layout)

        btnDataConn = findViewById(R.id.btn_data)
        btnDataConn.setOnClickListener(onClickListener)

        btnHotspot = findViewById(R.id.btn_hotspot)
        btnHotspot.setOnClickListener(onClickListener)
    }

    private var onClickListener = OnClickListener {
        if (it.id == R.id.btn_data) {
            openDataConnection()
        } else if (it.id == R.id.btn_hotspot) {
            openHotspot()
        }
    }

    fun openDataConnection() {
        btnDataConn.setText("Open Data (true)")

        Snackbar.make(btnDataConn, "Open Data Connection Button...", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
    }

    fun openHotspot() {
        btnHotspot.setText("Open Hotspot (true)")

        Snackbar.make(btnHotspot, "Open Hotspot Button!!!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
    }


    /**
     * 跳转Wifi设置
     * @param context
     */
    fun gotoWifiSetting(context: Context?) {
        try {
            val intent: Intent
            if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.GINGERBREAD_MR1)
                intent = Intent(Settings.ACTION_SETTINGS)
            else
                intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context!!.startActivity(intent)
        } catch (e: Exception) {
        }

    }

    /**
     * 跳转4G设置
     * @param context
     */
    fun goto4GSetting(context: Context) {
        try {
            val intent = Intent(Settings.ACTION_DATA_ROAMING_SETTINGS)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
            gotoWifiSetting(context)
        }

    }

    // Check current network whether is connected
    // return <MobileConnected, WirelessConnected>, return null if error
    fun checkConnected(context: Context): Pair<Boolean, Boolean> {
        var isMobileConnected = false
        var isWifiConnected = false

        try {
            val connectivity = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivity != null) {
                val networkInfo = connectivity.activeNetworkInfo
                if (networkInfo != null && networkInfo.isConnected) {
                    if (networkInfo.state == NetworkInfo.State.CONNECTED) {
                        val netType = networkInfo.type
                        if (netType == ConnectivityManager.TYPE_MOBILE) {
                            isMobileConnected = true
                        } else if (netType == ConnectivityManager.TYPE_WIFI) {
                            isWifiConnected = true
                        } else {
                            isMobileConnected = true
                        }
                    }
                }
            }
        } catch (e: Exception) {
            isMobileConnected = false
            isWifiConnected = false
        }

        return Pair(isMobileConnected, isWifiConnected)
    }

    fun isWifiConnect(context: Context): Boolean {
        val connect = checkConnected(context)
        return !connect.first && connect.second
    }

    fun isMobileConnect(context: Context): Boolean {
        val connect = checkConnected(context)
        return connect.first && !connect.second
    }

}
package com.meruga.geolocation

import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var geoLocationReceiver: GeoLocationBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        geoLocationReceiver = GeoLocationBroadcastReceiver()
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(
            geoLocationReceiver,
            IntentFilter("android.location.PROVIDERS_CHANGED")
        )
    }

    fun geoLocationON() {
        GeoLocationBroadcastReceiver.isFirstResponse = false
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(geoLocationReceiver)
    }
}
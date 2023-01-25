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
        // register thr broadcast for GeoLocationBroadcastReceiver
        registerReceiver(
            geoLocationReceiver,
            IntentFilter("android.location.PROVIDERS_CHANGED")
        )
    }

    /**
     * From UI user will click the button to turn the GeoLocation ON
     */
    fun geoLocationON() {
        GeoLocationBroadcastReceiver.isFirstResponse = false
    }

    override fun onDestroy() {
        super.onDestroy()
        // unregister thr broadcast for GeoLocationBroadcastReceiver
        unregisterReceiver(geoLocationReceiver)
    }
}
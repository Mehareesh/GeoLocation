package com.meruga.geolocation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.util.Log

class GeoLocationBroadcastReceiver : BroadcastReceiver() {

    private val TAG = "GeoLocationBroadcastRec"

    companion object {
        var isFirstResponse = true
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals("android.location.PROVIDERS_CHANGED")) {
            val locationManager: LocationManager =
                context?.getSystemService(Context.LOCATION_SERVICE)
                        as LocationManager
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER).let {
                Log.d(TAG, "isProviderEnabled : $it")
                if (it) {
                    if (GeoLocationManager.requestingGPSFromSettings) {
                        GeoLocationManager.getGeoLocation().isEnabled = true
                        GeoLocationManager.requestingGPSFromSettings = false
                    }
                    if (isFirstResponse && GeoLocationManager.isGeoLocationEnabled()) {
                        GeoLocationManager.setGeoLocationCurrentState(GeoLocation.GEO_LOCATION_ON)
                        GeoLocationManager.enableGeoLocationOnBroadcast();
                    }
                    isFirstResponse = false
                } else {
                    if (!isFirstResponse) {
                        GeoLocationManager.disableGeoLocationOnBroadcast()
                        isFirstResponse = true
                    }
                }
            }
        }
    }
}
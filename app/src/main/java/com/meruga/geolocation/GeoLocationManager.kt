package com.meruga.geolocation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object GeoLocationManager {

    private var geoLocationLiveData: MutableLiveData<GeoLocation> = MutableLiveData()
    private var geoLocationMainMenuState: String = GeoLocation.GEO_LOCATION_OFF
    private var geoLocation: GeoLocation = GeoLocation()

    var requestingGPSFromSettings = false

    fun getGeoLocation(): GeoLocation {
        return geoLocation
    }

    fun getGeoLocationLiveData(): LiveData<GeoLocation> {
        return geoLocationLiveData
    }

    /**
     * Based on the geolocation state, will take action over the logic and also the geolocation color
     */
    private fun setGeoLocationLiveData(geoLocationState: String) {

        when (geoLocationState) {

            // geolocation is turned ON
            GeoLocation.GEO_LOCATION_ON -> {
                updateGeoLocation(
                    true,
                    geoLocationState,
                    R.color.geoLocationON
                )
            }

            // geolocation is turned OFF
            GeoLocation.GEO_LOCATION_OFF -> {
                updateGeoLocation(
                    false,
                    geoLocationState,
                    R.color.geoLocationOFF
                )
            }

            // geolocation is ON but not following
            GeoLocation.GEO_LOCATION_DOES_NOT_FOLLOW -> {
                updateGeoLocation(
                    true,
                    geoLocationState,
                    R.color.geoLocationDoesNotFollow
                )
            }

            // geolocation is ON but unable to receive the position updates
            GeoLocation.GEO_LOCATION_POSITION_LOST -> {
                updateGeoLocation(
                    true,
                    geoLocationState,
                    R.color.geoLocationPositionLost,
                    geoLocation.currentState
                )
            }
        }
        geoLocationLiveData.value = geoLocation
    }

    /**
     * With respect to the geolocation state, we modify the geolocation properties
     */
    private fun updateGeoLocation(
        isEnabled: Boolean, currentState: String, drawableColor: Int,
        previousState: String = GeoLocation.GEO_LOCATION_OFF
    ) {
        geoLocation.apply {
            this.isEnabled = isEnabled
            this.currentState = currentState
            this.drawableColor = drawableColor
            this.previousState = previousState
        }
    }

    fun toggleGeoLocation() {
        if (isGeoLocationEnabled()) {
            geoLocationOFF()
        } else {
            geoLocationON()
        }
    }

    fun geoLocationON() {
        setGeoLocationLiveData(GeoLocation.GEO_LOCATION_ON)
    }

    fun geoLocationOFF() {
        setGeoLocationLiveData(GeoLocation.GEO_LOCATION_OFF)
    }

    fun geoLocationDoesNotFollow() {
        if (geoLocation.currentState.equals(GeoLocation.GEO_LOCATION_ON, ignoreCase = true)) {
            setGeoLocationLiveData(GeoLocation.GEO_LOCATION_DOES_NOT_FOLLOW)
        }
    }

    fun geoLocationPositionLost() {
        setGeoLocationLiveData(GeoLocation.GEO_LOCATION_POSITION_LOST)
    }

    fun isGeoLocationEnabled(): Boolean {
        return geoLocation.isEnabled
    }

    fun saveGeoLocationMainMenuState() {
        geoLocationMainMenuState = geoLocation.currentState;
        geoLocationOFF()
    }

    fun resetGeoLocationMainMenuState() {
        setGeoLocationLiveData(geoLocationMainMenuState)
    }

    fun setGeoLocationCurrentState(geoLocationState: String) {
        geoLocation.currentState = geoLocationState;
    }

    fun getGeoLocationCurrentState(): String {
        return geoLocation.currentState
    }

    fun getGeoLocationDrawableColor(): Int {
        return geoLocation.drawableColor
    }

    fun enableGeoLocationOnBroadcast() {
        var geoLocationState = getGeoLocationCurrentState()
        if (geoLocation.currentState == GeoLocation.GEO_LOCATION_POSITION_LOST) {
            geoLocationState = geoLocation.previousState
        }
        setGeoLocationLiveData(geoLocationState)
    }

    fun disableGeoLocationOnBroadcast() {
        var geoLocationState = GeoLocation.GEO_LOCATION_OFF
        if (geoLocation.isEnabled) {
            geoLocationState = GeoLocation.GEO_LOCATION_POSITION_LOST
        }
        setGeoLocationLiveData(geoLocationState)
    }
}
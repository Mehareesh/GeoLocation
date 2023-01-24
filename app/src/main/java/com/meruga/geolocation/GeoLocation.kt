package com.meruga.geolocation

class GeoLocation {

    companion object {
        const val GEO_LOCATION_ON: String = "GEO_LOCATION_ON"
        const val GEO_LOCATION_OFF: String = "GEO_LOCATION_OFF"
        const val GEO_LOCATION_DOES_NOT_FOLLOW: String = "GEO_LOCATION_DOES_NOT_FOLLOW"
        const val GEO_LOCATION_POSITION_LOST: String = "GEO_LOCATION_POSITION_LOST"
    }

    var isEnabled: Boolean = false
    var currentState: String = GEO_LOCATION_OFF
    var drawableColor: Int = R.color.geoLocationOFF
    var previousState: String = GEO_LOCATION_OFF
}
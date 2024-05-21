package com.project.chambaapp.api_services

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

class LocationTriggeredAPI(private val activity: Activity) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(activity)

    fun checkLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            activity, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestLocationPermission(requestCode: Int) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            requestCode
        )
    }

    fun getUserLocation(userId: Long, callback: (UserLocation?) -> Unit) {
        if (!checkLocationPermission()) {
            callback(null)
            return
        }

        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10000 // Intervalo de solicitud en milisegundos
            fastestInterval = 5000 // Intervalo más rápido en milisegundos
            numUpdates = 1 // Obtener solo una actualización de ubicación
            }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                fusedLocationClient.removeLocationUpdates(this) // Detener actualizaciones después de la primera
                val location: Location? = locationResult.lastLocation
                if (location != null) {
                    val userLocation = UserLocation(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        user_id = userId
                    )
                    callback(userLocation)
                } else {
                    callback(null)
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

}
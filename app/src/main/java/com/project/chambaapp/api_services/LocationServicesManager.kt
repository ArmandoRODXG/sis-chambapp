package com.project.chambaapp.api_services

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object LocationServicesManager{
    private const val PREFS_NAME = "LoggedPref"
    private const val KEY_LOGGED_USER_ID = "user_id"
    private const val KEY_LOGGED_USER_TYPE = "user_type"

    const val LOCATION_PERMISSION_REQUEST_CODE = 1
    const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(LocationServicesManager.PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun kickstartLocationService(appCompatActivity: AppCompatActivity){
        if (checkPermissions(appCompatActivity)) {
            startLocationService(appCompatActivity)
        } else {
            requestPermissions(appCompatActivity)
        }
    }

    private fun checkPermissions(appCompatActivity: AppCompatActivity): Boolean {
        val fineLocationPermission = ContextCompat.checkSelfPermission(
            appCompatActivity,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val coarseLocationPermission = ContextCompat.checkSelfPermission(
            appCompatActivity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return fineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                coarseLocationPermission == PackageManager.PERMISSION_GRANTED
    }

    fun startLocationService(appCompatActivity: AppCompatActivity) {
        val serviceIntent = Intent(appCompatActivity, LocationServiceAPI::class.java)
        ContextCompat.startForegroundService(appCompatActivity, serviceIntent)
    }

    fun stopLocationService(appCompatActivity: AppCompatActivity) {
        val serviceIntent = Intent(appCompatActivity, LocationServiceAPI::class.java)
        appCompatActivity.stopService(serviceIntent)
    }

     fun requestPermissions(appCompatActivity: AppCompatActivity) {
        ActivityCompat.requestPermissions(
            appCompatActivity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }



}
package com.uce.edu.ui.utilities

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.SettingsClient

class MyLocationManager(val context: Context) {
//    var context :Context ?= null
//    private lateinit var client :SettingsClient
//
//    private fun initVars(){
//        if(context !=null){
//            client = LocationServices.getSettingsClient(context!!)
//        }
//    }
//
//    public fun getUserLocation(){
//        initVars()
//    }

    private lateinit var client: SettingsClient

    private fun initVars() {
        if (context != null) {
            client = LocationServices.getSettingsClient(context!!)
        }
    }

    public fun getUserLocation() {
        initVars()
    }
}
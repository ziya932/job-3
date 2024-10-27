package com.example.locationshareddipitl404.viewModel_Dipti_amad_ict_l4_04

import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient

import com.google.android.gms.tasks.OnCompleteListener


class LocationViewModelDipti_amad_ict_L4_04:ViewModel() {

    private var fusedLocationClient: FusedLocationProviderClient? = null

    fun getLastLocation(callback: (String) -> Unit) {
        fusedLocationClient?.lastLocation
            ?.addOnCompleteListener(OnCompleteListener {
                if (it.isSuccessful && it.result != null) {
                    val lastLocation = it.result
                    val latitude = lastLocation.latitude
                    val longitude = lastLocation.longitude
                    val location = "Lat: $latitude, Long: $longitude"
                    callback(location)
                } else {
                    // Handle failure or missing permissions
                    callback("Location not available")
                }
            })
    }

    fun initializeFusedLocationClient(client: FusedLocationProviderClient) {
        fusedLocationClient = client
    }

}
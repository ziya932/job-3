package com.example.locationshareddipitl404.view


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.locationshareddipitl404.R
import com.example.locationshareddipitl404.databinding.ActivityMapsDiptiL404Binding
import com.example.locationshareddipitl404.viewModelDiptil404.FirestoreViewModelDiptil404

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class  MapsActivityDiptiL404 : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var firestoreViewModel: FirestoreViewModelDiptil404

     private val binding by lazy {
         ActivityMapsDiptiL404Binding.inflate(layoutInflater)
     }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        firestoreViewModel = ViewModelProvider(this).get(FirestoreViewModelDiptil404::class.java)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)

        binding.btnZoomIn.setOnClickListener {
            mMap.animateCamera(CameraUpdateFactory.zoomIn())
        }
        binding.btnZoomOut.setOnClickListener {
            mMap.animateCamera(CameraUpdateFactory.zoomOut())
        }
        binding.btnhomeOut.setOnClickListener {

            startActivity(Intent(this@MapsActivityDiptiL404, MainActivityDiptiL404::class.java))
            finish()
        }
    }

     override fun onMapReady(googleMap: GoogleMap) {
         mMap = googleMap
         firestoreViewModel.getAllUsers(this) {
             for (user in it) {
                 val userLocation = user.location
                 val latLng = parseLocation(userLocation)
                 if (userLocation.isEmpty()||userLocation == "Don't found any location yet"||userLocation == "Location not available") {
                     LatLng(37.4220936, -122.083922)
                 }else{
                     val markerOptions = MarkerOptions().position(latLng).title(user.displayName)
                     googleMap.addMarker(markerOptions)
                 }
                 val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
                 googleMap.animateCamera(cameraUpdate)
             }
         }
     }

     private fun parseLocation(location: String): LatLng {
         val latLngSplit = location.split(", ")
         val latitude = latLngSplit[0].substringAfter("Lat: ").toDouble()
         val longitude = latLngSplit[1].substringAfter("Long: ").toDouble()
         return LatLng(latitude, longitude)
     }

 }

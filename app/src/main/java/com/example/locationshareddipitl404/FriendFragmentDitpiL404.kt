package com.example.locationshareddipitl404

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.locationshareddipitl404.adapterdiptil404.UserAdapterDIPTIL404

import com.example.locationshareddipitl404.databinding.FragmentFriendDiptil404Binding
import com.example.locationshareddipitl404.view.MapsActivityDiptiL404
import com.example.locationshareddipitl404.viewModelDiptil404.AuthenticationViewModelDiptil404
import com.example.locationshareddipitl404.viewModelDiptil404.FirestoreViewModelDiptil404
import com.example.locationshareddipitl404.viewModelDiptil404.LocationViewModelDiptiL404
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

//
class FriendFragmentDitpiL404 : Fragment() {


   private  lateinit var  binding: FragmentFriendDiptil404Binding
   private  lateinit var  authenticationViewModel: AuthenticationViewModelDiptil404
    private  lateinit var  userAdapter: UserAdapterDIPTIL404
    private  lateinit var  firestoreViewModel: FirestoreViewModelDiptil404
    private lateinit var  locationViewModel: LocationViewModelDiptiL404
    private  lateinit var  fusedLocationClient: FusedLocationProviderClient


      val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getLocation()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Location Permission denied",
                    Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFriendDiptil404Binding.inflate(inflater, container, false)

        firestoreViewModel = ViewModelProvider(this).get(FirestoreViewModelDiptil404::class.java)
        authenticationViewModel= ViewModelProvider(this).get(AuthenticationViewModelDiptil404::class.java)
        locationViewModel = ViewModelProvider(this).get(LocationViewModelDiptiL404::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        locationViewModel.initializeFusedLocationClient(fusedLocationClient)
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        } else {

            getLocation()
        }

        userAdapter = UserAdapterDIPTIL404(emptyList())
        binding.userRV.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())

        }

        fetchUsers()

        binding.locationBtn.setOnClickListener{
            startActivity(Intent(requireContext(),  MapsActivityDiptiL404::class.java))
        }
        return binding.root
    }
    private fun fetchUsers() {
        firestoreViewModel.getAllUsers(requireContext()){
            userAdapter.updateData(it)
        }
    }
    private fun getLocation() {
        locationViewModel.getLastLocation {
            authenticationViewModel.getCurrentUserId().let { userId ->
                firestoreViewModel.updateUserLocation(requireContext(),userId, it)
            }
        }
    }

}






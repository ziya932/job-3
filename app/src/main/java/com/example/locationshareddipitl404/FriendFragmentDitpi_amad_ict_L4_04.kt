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
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.locationshareddipitl404.adapter_dipti_amad_ict_l4_04.UserAdapterDIPTI_amad_ict_L4_04

import com.example.locationshareddipitl404.databinding.FragmentFriendDiptiAmadIctL404Binding
import com.example.locationshareddipitl404.view_dipti_amad_ict_l4_04.MapsActivity_Dipti_amad_ict_L4_04
import com.example.locationshareddipitl404.viewModel_Dipti_amad_ict_l4_04.AuthenticationViewModel_Dipti_amad_ict_l4_04
import com.example.locationshareddipitl404.viewModel_Dipti_amad_ict_l4_04.FirestoreViewModel_Dipti_amad_ict_l4_04
import com.example.locationshareddipitl404.viewModel_Dipti_amad_ict_l4_04.LocationViewModelDipti_amad_ict_L4_04
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

//
class FriendFragmentDitpi_amad_ict_L4_04 : Fragment() {


   private  lateinit var  binding: FragmentFriendDiptiAmadIctL404Binding
   private  lateinit var  authenticationViewModel: AuthenticationViewModel_Dipti_amad_ict_l4_04
    private  lateinit var  userAdapter: UserAdapterDIPTI_amad_ict_L4_04
    private  lateinit var  firestoreViewModel: FirestoreViewModel_Dipti_amad_ict_l4_04
    private lateinit var  locationViewModel: LocationViewModelDipti_amad_ict_L4_04
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

        binding = FragmentFriendDiptiAmadIctL404Binding.inflate(inflater, container, false)

        firestoreViewModel = ViewModelProvider(this).get(FirestoreViewModel_Dipti_amad_ict_l4_04::class.java)
        authenticationViewModel= ViewModelProvider(this).get(AuthenticationViewModel_Dipti_amad_ict_l4_04::class.java)
        locationViewModel = ViewModelProvider(this).get(LocationViewModelDipti_amad_ict_L4_04::class.java)
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

        userAdapter = UserAdapterDIPTI_amad_ict_L4_04(emptyList())
        binding.userRV.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())

        }

        fetchUsers()

        binding.locationBtn.setOnClickListener{
            startActivity(Intent(requireContext(),  MapsActivity_Dipti_amad_ict_L4_04::class.java))
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






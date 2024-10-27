package com.example.locationshareddipitl404

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import com.example.locationshareddipitl404.databinding.FragmentProfilesDiptiL404Binding
import com.example.locationshareddipitl404.view.LoginActivityDiptiL404
import com.example.locationshareddipitl404.view.MainActivityDiptiL404
import com.example.locationshareddipitl404.viewModelDiptil404.AuthenticationViewModelDiptil404
import com.example.locationshareddipitl404.viewModelDiptil404.FirestoreViewModelDiptil404
import com.example.locationshareddipitl404.viewModelDiptil404.LocationViewModelDiptiL404
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.auth.FirebaseAuth


class ProfilesFragmentDiptiL404 : Fragment() {

    private lateinit var binding: FragmentProfilesDiptiL404Binding
    private lateinit var firestoreViewModel: FirestoreViewModelDiptil404
    private lateinit var authenticationViewModel: AuthenticationViewModelDiptil404
    private lateinit var loactionViewModel: LocationViewModelDiptiL404
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val firebaseAuth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentProfilesDiptiL404Binding.inflate(inflater, container, false)
        authenticationViewModel = ViewModelProvider(this).get(AuthenticationViewModelDiptil404::class.java)
        firestoreViewModel = ViewModelProvider(this).get(FirestoreViewModelDiptil404::class.java)
        loactionViewModel = ViewModelProvider(this).get(LocationViewModelDiptiL404::class.java)


        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(requireContext(), LoginActivityDiptiL404::class.java))

        }

        binding.homeBtn.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(requireContext(), MainActivityDiptiL404::class.java))
        }
        loadUserInfo()


        binding.updateBtn.setOnClickListener {
            val Newname = binding.nameEt.text.toString()
            val NewLocation = binding.locationEt.text.toString()

            updateUserInfo(Newname, NewLocation)
        }
        return binding.root
    }

    private fun updateUserInfo(newname: String, newLocation: String) {
        val currentUser = authenticationViewModel.getCurrentUser()
        if (currentUser != null) {
            val userId = currentUser.uid
            firestoreViewModel.updateUser(requireContext(), userId, newname, newLocation)
            Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
        } else {


            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
        }
    }


    private fun loadUserInfo() {
        val currentUser = authenticationViewModel.getCurrentUser()
        if (currentUser != null) {
          binding.emailEt .setText(currentUser.email)


            firestoreViewModel.getUser(requireContext(), currentUser.uid) {
                if (it != null) {
                    binding.nameEt.setText(it.displayName)


                    firestoreViewModel.getUserLocation (requireContext(), currentUser.uid) {
                        if (it.isNotEmpty()) {
                            binding.locationEt.setText(it)
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()

                }
            }
        } else {
            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()

        }
    }

}

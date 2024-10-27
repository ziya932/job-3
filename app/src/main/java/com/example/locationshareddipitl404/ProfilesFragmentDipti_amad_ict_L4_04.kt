package com.example.locationshareddipitl404

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.locationshareddipitl404.databinding. FragmentProfilesDiptiAmadIctL404Binding

import com.example.locationshareddipitl404.view_dipti_amad_ict_l4_04.LoginActivity_Dipti_amad_ict_L4_04
import com.example.locationshareddipitl404.view_dipti_amad_ict_l4_04.MainActivity_Dipti_amad_ict_L4_04
import com.example.locationshareddipitl404.viewModel_Dipti_amad_ict_l4_04.AuthenticationViewModel_Dipti_amad_ict_l4_04
import com.example.locationshareddipitl404.viewModel_Dipti_amad_ict_l4_04.FirestoreViewModel_Dipti_amad_ict_l4_04
import com.example.locationshareddipitl404.viewModel_Dipti_amad_ict_l4_04.LocationViewModelDipti_amad_ict_L4_04
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.auth.FirebaseAuth


class ProfilesFragmentDipti_amad_ict_L4_04 : Fragment() {

    private lateinit var binding: FragmentProfilesDiptiAmadIctL404Binding
    private lateinit var firestoreViewModel: FirestoreViewModel_Dipti_amad_ict_l4_04
    private lateinit var authenticationViewModel: AuthenticationViewModel_Dipti_amad_ict_l4_04
    private lateinit var loactionViewModel: LocationViewModelDipti_amad_ict_L4_04
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val firebaseAuth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentProfilesDiptiAmadIctL404Binding.inflate(inflater, container, false)
        authenticationViewModel = ViewModelProvider(this).get(AuthenticationViewModel_Dipti_amad_ict_l4_04::class.java)
        firestoreViewModel = ViewModelProvider(this).get(FirestoreViewModel_Dipti_amad_ict_l4_04::class.java)
        loactionViewModel = ViewModelProvider(this).get(LocationViewModelDipti_amad_ict_L4_04::class.java)


        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(requireContext(), LoginActivity_Dipti_amad_ict_L4_04::class.java))

        }

        binding.homeBtn.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(requireContext(), MainActivity_Dipti_amad_ict_L4_04::class.java))
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

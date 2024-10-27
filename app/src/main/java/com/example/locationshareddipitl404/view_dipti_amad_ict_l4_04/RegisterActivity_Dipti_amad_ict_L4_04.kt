package com.example.locationshareddipitl404.view_dipti_amad_ict_l4_04

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.locationshareddipitl404.databinding.ActivityRegisterDiptiAmadIctL404Binding
import com.example.locationshareddipitl404.viewModel_Dipti_amad_ict_l4_04.AuthenticationViewModel_Dipti_amad_ict_l4_04
import com.example.locationshareddipitl404.viewModel_Dipti_amad_ict_l4_04.FirestoreViewModel_Dipti_amad_ict_l4_04
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class RegisterActivity_Dipti_amad_ict_L4_04 : AppCompatActivity() {

    private  lateinit var  binding: ActivityRegisterDiptiAmadIctL404Binding
    lateinit var   authenticationViewModel: AuthenticationViewModel_Dipti_amad_ict_l4_04
    private  lateinit var  firestoreViewModel: FirestoreViewModel_Dipti_amad_ict_l4_04
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterDiptiAmadIctL404Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        authenticationViewModel = ViewModelProvider(this).get(AuthenticationViewModel_Dipti_amad_ict_l4_04::class.java)
        firestoreViewModel = ViewModelProvider(this).get(FirestoreViewModel_Dipti_amad_ict_l4_04::class.java)

        binding.loginTxt.setOnClickListener{

            startActivity(Intent(this@RegisterActivity_Dipti_amad_ict_L4_04, LoginActivity_Dipti_amad_ict_L4_04::class.java))
            finish()
        }

        binding.registerBtn.setOnClickListener{

            val name = binding.displayNameEt.text.toString()
            val  email = binding.emailEt.text.toString()
            val  password = binding.passwordEt.text.toString()
            val confirmPassword= binding.conPasswordEt.text.toString()
            val location = "Don't found any location yet "

            if(name.isEmpty() || email.isEmpty()||password.isEmpty()|| confirmPassword.isEmpty()){
                    Toast.makeText(this,"please fill al,l fields",Toast.LENGTH_SHORT).show()

            }
            else if(password!=confirmPassword){

                Toast.makeText(this,"password does not match",Toast.LENGTH_SHORT).show()
            }
            else if(password.length<6){

                Toast.makeText(this,"password must be at least 6 characters",Toast.LENGTH_SHORT).show()
            }
            else if (!android.util.Patterns.EMAIL_ADDRESS.matcher((email)).matches ()){

                Toast.makeText(this,"please enter valid email",Toast.LENGTH_SHORT).show()

            }
            else {
                authenticationViewModel.register(email, password, {

                    firestoreViewModel.saveUser(this,authenticationViewModel.getCurrentUserId(), name, email, location)

                    startActivity(Intent(this, MainActivity_Dipti_amad_ict_L4_04::class.java))

                    finish()

                }, {

                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                })
            }
        }


    }
    override fun onStart() {
        super.onStart()
        if(Firebase.auth.currentUser!=null){
            startActivity(Intent(this@RegisterActivity_Dipti_amad_ict_L4_04, MainActivity_Dipti_amad_ict_L4_04::class.java))
            finish()
        }
    }
}
package com.example.locationshareddipitl404.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.locationshareddipitl404.databinding.ActivityRegisterDiptil404Binding
import com.example.locationshareddipitl404.viewModelDiptil404.AuthenticationViewModelDiptil404
import com.example.locationshareddipitl404.viewModelDiptil404.FirestoreViewModelDiptil404
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class RegisterActivityDiptiL404 : AppCompatActivity() {

    private  lateinit var  binding: ActivityRegisterDiptil404Binding
    lateinit var   authenticationViewModel: AuthenticationViewModelDiptil404
    private  lateinit var  firestoreViewModel: FirestoreViewModelDiptil404
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterDiptil404Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        authenticationViewModel = ViewModelProvider(this).get(AuthenticationViewModelDiptil404::class.java)
        firestoreViewModel = ViewModelProvider(this).get(FirestoreViewModelDiptil404::class.java)

        binding.loginTxt.setOnClickListener{

            startActivity(Intent(this@RegisterActivityDiptiL404, LoginActivityDiptiL404::class.java))
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

                    startActivity(Intent(this, MainActivityDiptiL404::class.java))

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
            startActivity(Intent(this@RegisterActivityDiptiL404, MainActivityDiptiL404::class.java))
            finish()
        }
    }
}
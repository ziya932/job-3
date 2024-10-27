package com.example.locationshareddipitl404.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.locationshareddipitl404.databinding.ActivityLoginDiptil404Binding
import com.example.locationshareddipitl404.viewModelDiptil404.AuthenticationViewModelDiptil404

import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginActivityDiptiL404 : AppCompatActivity() {
    private  lateinit var  binding: ActivityLoginDiptil404Binding
    private  lateinit var  authenticationViewModel: AuthenticationViewModelDiptil404
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=  ActivityLoginDiptil404Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

         authenticationViewModel = ViewModelProvider(this).get(AuthenticationViewModelDiptil404::class.java)


        binding.loginBtn.setOnClickListener{
            val  email = binding.emailEt.text.toString()
            val  password = binding.passwordEt.text.toString()

            if(email.isEmpty()||password.isEmpty()) {
                Toast.makeText(this, "please fill al,l fields", Toast.LENGTH_SHORT).show()
            }
            else if (!android.util.Patterns.EMAIL_ADDRESS.matcher((email)).matches ()){
                Toast.makeText(this,"please enter valid email", Toast.LENGTH_SHORT).show()
            }
            else if(password.length<6){
                Toast.makeText(this,"password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            }
            else{
                authenticationViewModel.login(email,password, {
                    startActivity(Intent(this, MainActivityDiptiL404::class.java))
                    finish()
                },{
                    Toast.makeText(this,it, Toast.LENGTH_SHORT).show()
                })
            }

        }
        binding.registerTxt.setOnClickListener{
                startActivity(Intent(this, RegisterActivityDiptiL404::class.java))
                finish()
        }
    }
    override fun onStart() {
        super.onStart()
        if(Firebase.auth.currentUser!=null){
            startActivity(Intent(this@LoginActivityDiptiL404, MainActivityDiptiL404::class.java))
            finish()
        }
    }

}

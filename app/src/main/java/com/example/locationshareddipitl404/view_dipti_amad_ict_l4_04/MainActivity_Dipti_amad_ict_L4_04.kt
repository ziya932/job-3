package com.example.locationshareddipitl404.view_dipti_amad_ict_l4_04

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.locationshareddipitl404.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.example.locationshareddipitl404.databinding.ActivityLoginDiptiAmadIctL404Binding
import com.example.locationshareddipitl404.databinding.ActivityMainDiptiAmadIctL404Binding

class MainActivity_Dipti_amad_ict_L4_04 : AppCompatActivity() {
    lateinit var actionDrawerToggle: ActionBarDrawerToggle

    private val binding by lazy {
        ActivityMainDiptiAmadIctL404Binding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        var navController = findNavController(R.id.fragmentContainerView)
        binding.bottomBar.setupWithNavController(navController)
        binding.drawerNav.setupWithNavController(navController)

        actionDrawerToggle =
            ActionBarDrawerToggle(this, binding.drawerlayout, R.string.nav_open, R.string.nav_close)
        actionDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.drawerNav.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.logout -> {
                    Firebase.auth.signOut()
                    startActivity(Intent(this, LoginActivity_Dipti_amad_ict_L4_04::class.java))
                    finish()

                }
                R.id.profilesFragment -> {
                    navController.navigate(R.id.profilesFragment)
                }
                R.id.friendFragment -> {
                    navController.navigate(R.id.friendFragment)
                }
            }
            true
        }
        binding.bottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.logout -> {
                    Firebase.auth.signOut()
                    startActivity(Intent(this, LoginActivity_Dipti_amad_ict_L4_04::class.java))
                    finish()

                }
                R.id.friendFragment -> {
                    navController.navigate(R.id.friendFragment)
                }
                R.id.profilesFragment -> {
                    navController.navigate(R.id.profilesFragment)
                }

            }
            true

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}
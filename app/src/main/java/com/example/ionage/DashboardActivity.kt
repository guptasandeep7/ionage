package com.example.ionage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.ionage.databinding.ActivityDashBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
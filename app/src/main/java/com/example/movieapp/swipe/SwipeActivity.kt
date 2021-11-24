package com.example.movieapp.swipe

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivitySwipeBinding

class SwipeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySwipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySwipeBinding.inflate(layoutInflater)
        var userStat = intent.getStringExtra("UserStatus")//receive the information from the different Intents
        var databaseID = intent.getStringExtra("DatabaseID")
        // TODO: 24.11.2021 establish connection to the database
        setContentView(binding.root)
    }
}
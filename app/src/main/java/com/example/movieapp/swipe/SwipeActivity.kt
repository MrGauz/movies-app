package com.example.movieapp.swipe

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.database.Database
import com.example.movieapp.databinding.ActivitySwipeBinding

class SwipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySwipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySwipeBinding.inflate(layoutInflater)

        setContentView(binding.root)
        // TODO: 30.11.2021 Prevent users from going out of session
    }
}
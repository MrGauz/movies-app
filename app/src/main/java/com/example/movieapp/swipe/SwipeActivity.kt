package com.example.movieapp.swipe

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R
import com.example.movieapp.database.Database
import com.example.movieapp.databinding.ActivitySwipeBinding
import com.example.movieapp.models.IOnBackPressed

class SwipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySwipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySwipeBinding.inflate(layoutInflater)

        val fragment =
            this.supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container)
        (fragment as? IOnBackPressed)?.onBackPressed()?.not()?.let {
            super.onBackPressed()
        }

        setContentView(binding.root)
    }
}
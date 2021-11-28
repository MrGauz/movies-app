package com.example.movieapp.start

import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.movieapp.R
import com.example.movieapp.database.Database
import com.example.movieapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set device ID
        Database.deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

        // User joined via deep link
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        if (intent?.action == "android.intent.action.VIEW") {
            JoinFragment.sessionId = intent?.data?.getQueryParameter("id").toString()
            navController.navigate(R.id.joinFragment)
        }
    }
}
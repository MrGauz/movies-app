package com.example.movieapp.start

import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import com.example.movieapp.R
import com.example.movieapp.api.MoviesRepository
import com.example.movieapp.data.SessionData
import com.example.movieapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Disable dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set device ID
        SessionData.deviceId =
            Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

        // Load movie genres
        MoviesRepository.getGenres()

        // User joined via deep link
        if (intent?.action == "android.intent.action.VIEW") {
            JoinFragment.sessionId = intent?.data?.getQueryParameter("id").toString()
            findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.joinFragment)
        }
    }
}
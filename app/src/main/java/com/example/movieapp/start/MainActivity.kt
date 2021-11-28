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
    private var isHost: Boolean = false
    private lateinit var databaseId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Database.deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        /*if (intent?.action == "android.intent.action.VIEW") {
            // User joined via deep link
            isHost = false
            databaseId = intent?.data?.getQueryParameter("id").toString()
            navController.navigate(R.id.joinFragment)
        }
         */
    }
}
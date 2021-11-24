package com.example.movieapp.swipe

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.databinding.ActivitySwipeBinding

class SwipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySwipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySwipeBinding.inflate(layoutInflater)
        var userStat: String?
        var databaseId: String?

        //receive the information from the different Intents
        userStat = intent.getStringExtra("UserStatus")
        databaseId = intent.getStringExtra("DatabaseID")
        if (userStat == null || databaseId == null) { // If joining over deep link
            val data: Uri? = intent?.data
            userStat = "user" //people joining via deep link are always users
            databaseId = data?.getQueryParameter("key").toString()
        }
        Toast.makeText(this, "$userStat|$databaseId", Toast.LENGTH_SHORT).show()

        // TODO: 24.11.2021 establish connection to the database
        //using the parameters databaseId and userStat
        setContentView(binding.root)
    }
}
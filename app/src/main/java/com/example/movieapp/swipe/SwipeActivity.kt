package com.example.movieapp.swipe

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.databinding.ActivitySwipeBinding

class SwipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySwipeBinding
    private var userStat: String? = null
    private var databaseId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySwipeBinding.inflate(layoutInflater)
        initvars()
        //receive the information from the different Intents
        setContentView(binding.root)
        // TODO: 24.11.2021 establish connection to the database
        //using the parameters databaseId and userStat
    }

    fun initvars(){
        userStat = intent.getStringExtra("UserStatus")
        databaseId = intent.getStringExtra("DatabaseID")
        if (userStat == null || databaseId == null) { // If joining over deep link
            val data: Uri? = intent?.data
            userStat = "user" //people joining via deep link are always users
            databaseId = data?.getQueryParameter("key").toString()
        }
        Toast.makeText(this, "$userStat|$databaseId", Toast.LENGTH_SHORT).show()
    }
}
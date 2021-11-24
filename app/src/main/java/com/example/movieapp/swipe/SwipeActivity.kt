package com.example.movieapp.swipe

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
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
        var userStat : String?
        var databaseID : String?

        //Toast.makeText(this, path +"|"+otherdata, Toast.LENGTH_SHORT).show()
        userStat = intent.getStringExtra("UserStatus")//receive the information from the different Intents
        databaseID = intent.getStringExtra("DatabaseID")
        if(userStat == null || databaseID == null){ // If joining over deep link
            val data : Uri?= intent?.data
            userStat = "user" //people joining via deep link are always users
            databaseID = data?.getQueryParameter("key").toString()
        }
        Toast.makeText(this, userStat + "|" + databaseID, Toast.LENGTH_SHORT).show()

        // TODO: 24.11.2021 establish connection to the database
        //using the parameters databaseId and userStat
        setContentView(binding.root)
    }
}
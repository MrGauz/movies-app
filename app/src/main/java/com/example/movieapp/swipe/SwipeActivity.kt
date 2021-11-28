package com.example.movieapp.swipe

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.database.Database
import com.example.movieapp.databinding.ActivitySwipeBinding

class SwipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySwipeBinding
    private var isHost: Boolean = false
    private lateinit var databaseId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySwipeBinding.inflate(layoutInflater)

        if (intent?.action == "android.intent.action.VIEW") {
            // User joined via deep link
            isHost = false
            databaseId = intent?.data?.getQueryParameter("id").toString()
            // TODO: move this logic to JoinFragment to check if database ID exists
        } else {
            // User started the session or joined from within the app
            isHost = intent.getBooleanExtra("isHost", false)
            databaseId = Database.sessionId
        }

        Toast.makeText(this, "$isHost | $databaseId", Toast.LENGTH_SHORT).show()

        setContentView(binding.root)
    }
}
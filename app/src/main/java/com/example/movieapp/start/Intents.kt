package com.example.movieapp.start

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.movieapp.swipe.SwipeActivity

class Intents(private val isHost: Boolean, private val context: Context?) {

    fun intentToSwipe() {
        val intent = Intent(this.context, SwipeActivity::class.java);
        intent.putExtra("isHost", isHost)
        this.context?.let { startActivity(it, intent, null) }
    }
}
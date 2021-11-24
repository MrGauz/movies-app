package com.example.movieapp.start

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.movieapp.swipe.SwipeActivity

class Intents {
    private val databaseID: String
    private val userStatus : String
    private val context : Context?
    constructor(databaseID : String, userStatus : String, context: Context?){
        this.userStatus = userStatus
        this.databaseID = databaseID
        this.context = context
    }
    fun intentToSwipe(){
        val intent = Intent(this.context, SwipeActivity::class.java);
        intent.putExtra("UserStatus", userStatus)
        intent.putExtra("DatabaseID", databaseID)
        this.context?.let { startActivity(it,intent,null) }
    }
}
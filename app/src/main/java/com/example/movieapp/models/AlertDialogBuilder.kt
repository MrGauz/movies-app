package com.example.movieapp.models

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Paint
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import com.example.movieapp.R
import com.example.movieapp.data.SessionData

class AlertDialogBuilder {
    fun createDialog(context: Context?, activity:FragmentActivity?,style: Int) : AlertDialog { //creates the alert, if user wants to leave he gets kicked out of the session and the app, otherwise nothing happens
        val alertDialogBuilder = AlertDialog.Builder(context, style)
        alertDialogBuilder.setTitle("Exit App")
        alertDialogBuilder.setMessage("Are you sure you want to exit?")
        alertDialogBuilder.setPositiveButton("Yes", { dialogInterface: DialogInterface, i: Int ->
            SessionData.leaveSession()
            activity?.moveTaskToBack(true)
            activity?.finish()
        })
        alertDialogBuilder.setNegativeButton("Cancel", { dialogInterface: DialogInterface, i: Int -> })

        return alertDialogBuilder.create()
    }
    fun createDialogOnBackButtonPress(context: Context?, activity:FragmentActivity?,style: Int){
        val alertDialog =createDialog(context,activity,style)
        if (activity != null) {
            activity//on back press this launches the alert
                .onBackPressedDispatcher
                .addCallback(activity, object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        alertDialog.show()
                    }
                })
        }
    }
}
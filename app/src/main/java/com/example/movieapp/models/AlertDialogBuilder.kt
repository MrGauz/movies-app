package com.example.movieapp.models

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.example.movieapp.R
import com.example.movieapp.database.Database
import com.example.movieapp.swipe.SwipeFragment

/**
 * Creates the alert,
 * if user wants to leave
 *      he gets kicked out of the session and the app,
 * otherwise
 *      nothing happens
 */
class AlertDialogBuilder {
    fun createDialog(
        context: Context?,
        activity: FragmentActivity?,
        style: Int
    ): AlertDialog {

        val alertDialogBuilder = AlertDialog.Builder(context, style)
        alertDialogBuilder.setTitle("Exit App")
        alertDialogBuilder.setMessage("Are you sure you want to exit?")
        alertDialogBuilder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
            Database.leaveSession()
            activity?.moveTaskToBack(true)
            activity?.finish()
        }
        alertDialogBuilder.setNegativeButton(
            "Cancel"
        ) { _: DialogInterface, _: Int -> }

        return alertDialogBuilder.create()
    }

    /**
     * On back press this launches the alert
     */
    fun createDialogOnBackButtonPress(context: Context?, activity: FragmentActivity?, style: Int, fragmentID : Int) {
        val alertDialog = createDialog(context, activity, style)
        activity?.onBackPressedDispatcher?.addCallback(
            activity,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (fragmentID == Navigation.findNavController
                            (activity, activity.supportFragmentManager.primaryNavigationFragment!!.id).
                        currentDestination?.id){
                        alertDialog.show()
                    }
                    else{
                        isEnabled = false
                        activity.onBackPressed()
                    }
                }
            })
    }
}
package com.example.movieapp.start

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentDirectorBinding
import com.example.movieapp.databinding.FragmentShareBinding
import com.example.movieapp.swipe.SwipeActivity

class ShareFragment : Fragment() {
    private var _binding: FragmentShareBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShareBinding.inflate(inflater, container, false)
        val startSwipeButton= binding.startSwiping
        val shareButton = binding.shareLink
        startSwipeButton.setOnClickListener{
            val swipeIntent = Intents("todo","user",this.context)
            // TODO: 24.11.2021 the database ID has to be passed here somehow
            swipeIntent.intentToSwipe()
        }
        shareButton.setOnClickListener{
            // TODO: 24.11.2021 make intent to share deep link to swipe activity
            Toast.makeText(this.context, "this function hasnt been implemented yet", Toast.LENGTH_SHORT).show()
        }
        val root: View = binding.root
        return root
    }
}
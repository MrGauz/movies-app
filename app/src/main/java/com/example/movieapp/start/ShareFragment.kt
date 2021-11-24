package com.example.movieapp.start

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
            // TODO: 24.11.2021 call the swipe activity with an intent
            Toast.makeText(this.context, "this function hasnt been implemented yet", Toast.LENGTH_SHORT).show()
        }
        shareButton.setOnClickListener{
            // TODO: 24.11.2021 make intent to share deep link to swipe activity
            Toast.makeText(this.context, "this function hasnt been implemented yet", Toast.LENGTH_SHORT).show()
        }
        val root: View = binding.root
        return root
    }
}
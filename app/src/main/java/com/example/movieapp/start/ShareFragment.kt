package com.example.movieapp.start

import android.content.ActivityNotFoundException
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
        // TODO: 24.11.2021 generate a database id and pass it to the intent
        val databaseId = "" //todo
        val startSwipeButton= binding.startSwiping
        val shareButton = binding.shareLink
        startSwipeButton.setOnClickListener{
            val swipeIntent = Intents(databaseId,"admin",this.context)
            swipeIntent.intentToSwipe()
        }
        shareButton.setOnClickListener{
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(Intent.EXTRA_TEXT,"Hi there, let's watch a movie together: https://www.meineurl.com/path?key="+databaseId)
            try {
                startActivity(intent)
            }catch (e: ActivityNotFoundException){
                Toast.makeText(this.context, "couldn't find a matching application on your device", Toast.LENGTH_SHORT).show()
            }
        }
        val root: View = binding.root
        return root
    }
}
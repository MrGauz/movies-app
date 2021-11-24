package com.example.movieapp.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.databinding.FragmentJoinBinding

class JoinFragment : Fragment() {
    private var _binding: FragmentJoinBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJoinBinding.inflate(inflater, container, false)
        val joinButton = binding.joinButton
        joinButton.setOnClickListener {

            val swipeIntent = Intents("todo", "user", this.context)
            // TODO: 24.11.2021 the database ID has to be passed here somehow
            swipeIntent.intentToSwipe()
        }
        return binding.root
    }
}
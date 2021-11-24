package com.example.movieapp.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentFilterScreenBinding
import com.example.movieapp.databinding.FragmentJoinBinding

class JoinFragment : Fragment() {
    private var _binding: FragmentJoinBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJoinBinding.inflate(inflater, container, false)
        val joinButton = binding.joinButton
        joinButton.setOnClickListener {
            Toast.makeText(this.context, "this function has not been implemented yet", Toast.LENGTH_SHORT).show()
            //todo: Call swipe activity with intent
        }
        val root: View = binding.root
        return root
    }
}
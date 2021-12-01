package com.example.movieapp.swipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.database.MatchesViewModelFactory
import com.example.movieapp.databinding.FragmentMatchesBinding
import com.example.movieapp.models.AlertDialogBuilder
import com.example.movieapp.models.MatchesViewModel

class MatchesFragment : Fragment() {
    private var _binding: FragmentMatchesBinding? = null
    private val binding get() = _binding!!

    private var viewManager = LinearLayoutManager(context)
    private lateinit var matchesViewModel: MatchesViewModel
    private lateinit var matchesRecycler: RecyclerView
    private var matchesCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchesBinding.inflate(inflater, container, false)

        val factory = MatchesViewModelFactory()
        matchesViewModel = ViewModelProviders.of(this, factory).get(MatchesViewModel::class.java)

        matchesRecycler = binding.matchesList
        matchesRecycler.layoutManager = viewManager
        matchesViewModel.getMatches().observe(viewLifecycleOwner, { matches ->
            matchesCount = matches.size
            matchesRecycler.adapter = MatchesRecyclerAdapter(matches, requireContext())
        })

        binding.backButton.setOnClickListener {
            if (matchesCount >= 3) {
                AlertDialogBuilder().createDialogOnBackButtonPress(
                    this.context,
                    activity,
                    R.style.AlertDialog
                )
            } else {
                binding.root.findNavController()
                    .navigate(R.id.action_matchesFragment_to_swipeFragment)
            }
        }

        return binding.root
    }
}
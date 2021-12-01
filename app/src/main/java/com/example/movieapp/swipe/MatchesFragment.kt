package com.example.movieapp.swipe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.database.MatchesViewModelFactory
import com.example.movieapp.databinding.FragmentMatchesBinding
import com.example.movieapp.models.AlertDialogBuilder
import com.example.movieapp.models.MatchesViewModel
import com.example.movieapp.models.Movie

class MatchesFragment : Fragment() {
    private var _binding: FragmentMatchesBinding? = null
    private val binding get() = _binding!!

    private var viewManager = LinearLayoutManager(context)
    private lateinit var matchesViewModel: MatchesViewModel
    private lateinit var matchesRecycler: RecyclerView
    private lateinit var matches: List<Movie>
    private var shareText = ""

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
            this.matches = matches
            shareText = "Hey, here's what we decided to watch:\n" +
                    matches.joinToString(separator = "\n") { m -> "  - ${m.title}" }
            if (this.matches.isNotEmpty()) {
                binding.shareMatchesButton.visibility = View.VISIBLE
            }
            matchesRecycler.adapter = MatchesRecyclerAdapter(matches, requireContext())
        })

        binding.backButton.setOnClickListener {
            if (matches.size >= 3) {
                AlertDialogBuilder().createDialog(
                    this.context,
                    activity,
                    R.style.AlertDialog
                ).show()
            } else {
                binding.root.findNavController()
                    .navigate(R.id.action_matchesFragment_to_swipeFragment)
            }
        }
        createDialogOnBackButtonPressCustom(
            this.context,
            activity,
            R.style.AlertDialog,
            R.id.matchesFragment
        )

        // Share matches button
        binding.shareMatchesButton.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareText)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        return binding.root
    }

    private fun createDialogOnBackButtonPressCustom(
        context: Context?,
        activity: FragmentActivity?,
        style: Int,
        fragmentID: Int
    ) {
        val alertDialog = AlertDialogBuilder().createDialog(context, activity, style)
        activity?.onBackPressedDispatcher?.addCallback(
            activity,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (fragmentID == Navigation.findNavController(
                            activity,
                            activity.supportFragmentManager.primaryNavigationFragment!!.id
                        ).currentDestination?.id && matches.size >= 3
                    ) {
                        alertDialog.show()
                    } else {
                        isEnabled = false
                        activity.onBackPressed()
                    }
                }
            })
    }
}
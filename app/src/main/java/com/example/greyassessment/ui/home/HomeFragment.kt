package com.example.greyassessment.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.greyassessment.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _viewBinding: FragmentHomeBinding? = null
    private val binding get() = _viewBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            setupViews()
    }

    private fun setupViews() {
        binding.repoCard.setOnClickListener { navigateToRepoFragment()}
        binding.userCard.setOnClickListener { navigateToUserFragment() }
    }

    private fun navigateToUserFragment() {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUsersFragment())
    }

    private fun navigateToRepoFragment() {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToRepoFragment())
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }

}
package com.example.greyassessment.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greyassessment.R
import com.example.greyassessment.databinding.FragmentUsersBinding
import com.example.greyassessment.ui.model.UserDetail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersFragment : Fragment() {

    private var _viewBinding: FragmentUsersBinding? = null
    private val binding get() = _viewBinding!!

    private val viewModel by viewModels<UsersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentUsersBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    when (it) {
                        is UsersViewModel.UiState.Default -> setupViews()
                        is UsersViewModel.UiState.Error -> showError()
                        is UsersViewModel.UiState.Loaded -> showLoaded(it.users)
                        is UsersViewModel.UiState.Loading -> showLoading()
                    }
                }
        }

    }

    private fun setupViews() {
        binding.searchLayout.searchEditText.setHint(R.string.search_for_users)
        binding.emptySearchLayout.text.setText(R.string.search_github_for_users)
        binding.searchLayout.searchButton.setOnClickListener { search() }
    }

    private fun search() {
        val username: String = binding.searchLayout.searchEditText.text.toString()
        if (username.isNotBlank()) {
            viewModel.getGithubUsers(username)
        }
    }

    private fun showError() {
        binding.emptySearchLayout.text.setText(R.string.user_error_message)
        binding.emptySearchLayout.emptySearchView.visibility = View.VISIBLE
        binding.usersRv.visibility = View.GONE
        binding.usersProgressBar.visibility = View.GONE
    }

    private fun showLoading() {
        binding.emptySearchLayout.emptySearchView.visibility = View.GONE
        binding.usersRv.visibility = View.GONE
        binding.usersProgressBar.visibility = View.VISIBLE
    }

    private fun showLoaded(users: List<UserDetail>) {
        if (users.isEmpty()) {
            showError()
            return
        }
        binding.searchLayout.searchEditText.setText("")
        binding.emptySearchLayout.emptySearchView.visibility = View.GONE
        binding.usersRv.visibility = View.VISIBLE
        binding.usersProgressBar.visibility = View.GONE
        val userAdapter = UserAdapter {
            navigateToUserDetail(it)
        }
        binding.usersRv.apply {
            layoutManager = LinearLayoutManager(
                context
            )
            adapter = userAdapter
        }
        userAdapter.submitList(users)
    }

    private fun navigateToUserDetail(user: UserDetail) {
        findNavController().navigate(
            UsersFragmentDirections.actionUsersFragmentToUserDetailFragment(
                user
            )
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }

}
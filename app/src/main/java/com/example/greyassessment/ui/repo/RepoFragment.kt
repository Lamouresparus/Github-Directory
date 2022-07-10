package com.example.greyassessment.ui.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greyassessment.R
import com.example.greyassessment.databinding.FragmentRepoBinding
import com.example.greyassessment.ui.model.Repo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepoFragment : Fragment() {

    private var _viewBinding: FragmentRepoBinding? = null
    private val binding get() = _viewBinding!!

    private val viewModel by viewModels<RepoViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentRepoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect {
                when (it) {
                    is RepoViewModel.UiState.Default -> setupViews()
                    is RepoViewModel.UiState.Error -> {
                        showError()
                    }
                    is RepoViewModel.UiState.Loaded -> showLoaded(it.repos)
                    is RepoViewModel.UiState.Loading -> showLoading()
                }
            }
        }
    }

    private fun setupViews() {
        binding.searchLayout.searchEditText.setHint(R.string.search_for_repositories)
        binding.emptySearchLayout.text.setText(R.string.search_github_for_repositories)
        binding.searchLayout.searchButton.setOnClickListener { search() }
    }

    private fun search() {
        val repo: String = binding.searchLayout.searchEditText.text.toString()
        if (repo.isNotBlank()) {
            viewModel.getGithubRepo(repo)
        }
    }

    private fun showError() {
        binding.emptySearchLayout.text.setText(R.string.repository_error_message)
        binding.emptySearchLayout.emptySearchView.visibility = View.VISIBLE
        binding.repoRv.visibility = View.GONE
        binding.repoProgressBar.visibility = View.GONE
    }

    private fun showLoading() {
        binding.emptySearchLayout.emptySearchView.visibility = View.GONE
        binding.repoRv.visibility = View.GONE
        binding.repoProgressBar.visibility = View.VISIBLE
    }

    private fun showLoaded(repos: List<Repo>) {
        if (repos.isEmpty()) {
            showError()
            return
        }
        binding.searchLayout.searchEditText.setText("")
        binding.emptySearchLayout.emptySearchView.visibility = View.GONE
        binding.repoRv.visibility = View.VISIBLE
        binding.repoProgressBar.visibility = View.GONE
        val repoAdapter = RepoAdapter()
        binding.repoRv.apply {
            layoutManager = LinearLayoutManager(
                context
            )
            adapter = repoAdapter
        }
        repoAdapter.submitList(repos)
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }
}
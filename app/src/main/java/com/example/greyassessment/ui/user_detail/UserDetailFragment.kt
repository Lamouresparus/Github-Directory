package com.example.greyassessment.ui.user_detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.greyassessment.R
import com.example.greyassessment.databinding.FragmentUserDetailBinding
import com.example.greyassessment.ui.model.Repo
import com.example.greyassessment.ui.model.UserDetail
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class UserDetailFragment : Fragment() {

    private var _viewBinding: FragmentUserDetailBinding? = null
    private val binding get() = _viewBinding!!

    private val user: UserDetailFragmentArgs by navArgs()

    private val viewModel by viewModels<UserDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(user.userDetail)

        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is UserDetailViewModel.UiState.Error -> showError()
                is UserDetailViewModel.UiState.Loaded -> showRepo(it.users)
                is UserDetailViewModel.UiState.Loading -> showLoading()
            }
        }
    }

    private fun showLoading() {
        binding.userDetailRepoLayout.userDetailRepoRv.visibility = View.GONE
        binding.emptySearchLayout.emptySearchView.visibility = View.GONE
        binding.userRepoProgressBar.visibility = View.VISIBLE
    }

    private fun showError() {
        binding.userDetailRepoLayout.userDetailRepoRv.visibility = View.GONE
        binding.userRepoProgressBar.visibility = View.GONE
        binding.emptySearchLayout.imageView3.setImageResource(R.drawable.ic_empty_repo)
        binding.emptySearchLayout.text.text = getString(R.string.empty_user_repo_message)
        binding.emptySearchLayout.emptySearchView.visibility = View.VISIBLE
    }

    private fun setupViews(userDetail: UserDetail) {
        viewModel.getGithubUsers(userDetail.username)
        userDetail.apply {
            binding.userDetailName.text = name.ifBlank { getString(R.string.github_user) }
            binding.userDetailImage.load(avatarUrl)
            binding.userDetailUsername.text = username
            binding.userDetailBio.text = bio.ifBlank { getString(R.string.no_bio) }
            binding.userDetailLocation.text =
                location.ifBlank { getString(R.string.unknown_location) }
            binding.userDetailFollowers.text = getFollowers(followers)
            val following = "$following following"
            binding.userDetailFollowing.text = following
            if (userDetail.blog.isBlank()) {
                binding.linkImage.visibility = View.GONE
            } else {
                binding.userWebsite.text = userDetail.blog
            }
        }
    }

    private fun showRepo(repos: List<Repo>) {
        if (repos.isEmpty()) {
            showError()
            return
        }
        binding.emptySearchLayout.emptySearchView.visibility = View.GONE
        binding.userRepoProgressBar.visibility = View.GONE
        binding.userDetailRepoLayout.textView5.text = repos.size.toString()
        binding.userDetailRepoLayout.userDetailRepoRv.visibility = View.VISIBLE
        val repoAdapter = UserDetailRepoAdapter()
        binding.userDetailRepoLayout.recyclerView.apply {
            layoutManager = LinearLayoutManager(
                context
            )
            adapter = repoAdapter
        }
        repoAdapter.submitList(repos)
    }

    private fun getFollowers(followers: Int): String {
        val followerString = if (followers == 1) "follower ." else "followers ."
        return "$followers $followerString"
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }

}
package com.example.greyassessment.ui.user_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.greyassessment.R
import com.example.greyassessment.databinding.FragmentUserDetailBinding
import com.example.greyassessment.ui.model.UserDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : Fragment() {

    private var _viewBinding: FragmentUserDetailBinding? = null
    private val binding get() = _viewBinding!!

    private val user: UserDetailFragmentArgs by navArgs()

    private lateinit var viewModel: UserDetailViewModel

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
    }

    private fun setupViews(userDetail: UserDetail) {
        userDetail.apply {
            binding.userDetailName.text = name.ifBlank { getString(R.string.github_user) }
            binding.userDetailImage.load(avatarUrl)
            binding.userDetailUsername.text = username
            binding.userDetailBio.text = bio.ifBlank { getString(R.string.no_bio) }
            binding.userDetailLocation.text =
                location.ifBlank { getString(R.string.unknown_location) }
            binding.userDetailFollowers.text = getFollowers(followers)
            binding.userDetailFollowing.text = "$following following"
            if (userDetail.blog.isBlank()) {
                binding.linkImage.visibility = View.GONE
            } else {
                binding.userWebsite.text = userDetail.blog
            }
        }
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
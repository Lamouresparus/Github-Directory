package com.example.greyassessment.ui.repo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.greyassessment.databinding.RepoItemViewBinding
import com.example.greyassessment.ui.model.Repo

class RepoAdapter :
    ListAdapter<Repo, RepoAdapter.RepoViewHolder>(Repo.DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = RepoItemViewBinding.inflate(LayoutInflater.from(parent.context))
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RepoViewHolder(private val binding: RepoItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Repo) {
            // set data to views
            val username = item.owner.username + "/"
            binding.repoUsername.text = username
            binding.repoName.text = item.name
            binding.repoStars.text = item.watchers.toString()
            binding.repoLanguage.text = item.language.ifBlank { "nil" }
            binding.repoDescription.text =
                item.description.ifBlank { "This Repository has no description" }
            binding.userImage.load(item.owner.avatarUrl)
            bindTags(item)

        }

        private fun bindTags(item: Repo) {
            val topics = item.topics as MutableList<String>

            if (topics.isNotEmpty()) {
                binding.repoTag1.text = item.topics.first()
                topics.removeFirst()
                if (topics.isNotEmpty()) {
                    binding.repoTag2.text = item.topics.first()
                    topics.removeFirst()
                } else {
                    binding.materialCardView2.visibility = View.GONE
                }
                if (topics.isNotEmpty()) {
                    binding.repoTag3.text = item.topics.first()
                    topics.removeFirst()
                } else {
                    binding.materialCardView4.visibility = View.GONE

                }
            } else {
                binding.tagsGroup.visibility = View.GONE
            }

        }

    }
}
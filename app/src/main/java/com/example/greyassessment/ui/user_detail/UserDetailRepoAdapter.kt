package com.example.greyassessment.ui.user_detail

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.greyassessment.databinding.UserDetailRepoItemViewBinding
import com.example.greyassessment.ui.model.Repo
import com.example.greyassessment.ui.util.getDate

@RequiresApi(Build.VERSION_CODES.O)
class UserDetailRepoAdapter :
    ListAdapter<Repo, UserDetailRepoAdapter.RepoViewHolder>(Repo.DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = UserDetailRepoItemViewBinding.inflate(LayoutInflater.from(parent.context))
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RepoViewHolder(private val binding: UserDetailRepoItemViewBinding) :
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
            binding.updatedAt.text = item.pushed_at.getDate()

        }

    }
}
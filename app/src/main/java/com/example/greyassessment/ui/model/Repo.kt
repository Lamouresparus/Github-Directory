package com.example.greyassessment.ui.model

import androidx.recyclerview.widget.DiffUtil


data class Repo(
    val name: String,
    val owner: User,
    val watchers: Int,
    val language: String,
    val topics: List<String>,
    val description: String,
    val repoUrl: String,
    val pushed_at: String
) {
    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.repoUrl == newItem.repoUrl
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem == newItem
            }

        }
    }
}
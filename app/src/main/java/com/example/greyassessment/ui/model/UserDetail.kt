package com.example.greyassessment.ui.model

import androidx.recyclerview.widget.DiffUtil


data class UserDetail(
    val username: String,
    val avatarUrl: String,
    val githubUrl: String,
    val name: String,
    val location: String,
    val followers: Int,
    val following: Int,
    val repos: Int,
    val bio: String,
    val email: String,
    val blog: String
) {
    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<UserDetail>() {
            override fun areItemsTheSame(oldItem: UserDetail, newItem: UserDetail): Boolean {
                return oldItem.username == newItem.username
            }

            override fun areContentsTheSame(oldItem: UserDetail, newItem: UserDetail): Boolean {
                return oldItem == newItem
            }

        }
    }
}
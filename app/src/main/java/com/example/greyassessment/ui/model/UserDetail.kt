package com.example.greyassessment.ui.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
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
) : Parcelable

class DIFF_UTIL : DiffUtil.ItemCallback<UserDetail>() {
    override fun areItemsTheSame(oldItem: UserDetail, newItem: UserDetail): Boolean {
        return oldItem.username == newItem.username
    }

    override fun areContentsTheSame(oldItem: UserDetail, newItem: UserDetail): Boolean {
        return oldItem == newItem
    }
}


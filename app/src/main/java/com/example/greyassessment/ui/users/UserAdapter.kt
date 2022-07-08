package com.example.greyassessment.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.greyassessment.databinding.UserItemViewBinding
import com.example.greyassessment.ui.model.UserDetail

class UserAdapter(private val onClick: (UserDetail) -> Unit) :
    ListAdapter<UserDetail, UserAdapter.UserViewHolder>(UserDetail.DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemViewBinding.inflate(LayoutInflater.from(parent.context))
        return UserViewHolder(binding) { onClick.invoke(getItem(it)) }
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class UserViewHolder(private val binding: UserItemViewBinding, onClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
//            onClick(adapterPosition)
        }

        fun bind(item: UserDetail) {
            // set data to views

            binding.username.text = item.username
            binding.bio.text = item.bio.ifBlank { "No Bio" }
            binding.email.text = item.email
            binding.fullName.text = item.name.ifBlank { "Github User" }
            binding.location.text = item.location.ifBlank { "Unknown location" }
            binding.userImage.load(item.avatarUrl)
        }

    }
}
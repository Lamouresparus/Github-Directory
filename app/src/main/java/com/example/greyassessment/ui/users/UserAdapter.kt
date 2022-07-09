package com.example.greyassessment.ui.users

import android.content.res.Resources.getSystem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.greyassessment.databinding.UserItemViewBinding
import com.example.greyassessment.ui.model.DIFF_UTIL
import com.example.greyassessment.ui.model.UserDetail

class UserAdapter(private val onClick: (UserDetail) -> Unit) :
    ListAdapter<UserDetail, UserAdapter.UserViewHolder>(DIFF_UTIL()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemViewBinding.inflate(LayoutInflater.from(parent.context))
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserViewHolder(private val binding: UserItemViewBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }


        fun bind(item: UserDetail) {
            binding.username.text = item.username
            binding.bio.text = item.bio.ifBlank {"No Bio"}
            binding.email.text = item.email
            binding.fullName.text = item.name.ifBlank {"Github User" }
            binding.location.text = item.location.ifBlank {"Unknown location" }
            binding.userImage.load(item.avatarUrl)

        }

        override fun onClick(v: View?) {
            onClick(currentList[adapterPosition])
        }

    }
}
package com.example.greyassessment.ui.user_detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greyassessment.R

class UserDetailFragment : Fragment() {

    companion object {
        fun newInstance() = UserDetailFragment()
    }

    private lateinit var viewModel: UserDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_detail, container, false)
    }


}
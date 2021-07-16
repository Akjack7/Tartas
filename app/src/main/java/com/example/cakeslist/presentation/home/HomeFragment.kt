package com.example.cakeslist.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.cakeslist.R
import org.koin.android.viewmodel.ext.android.viewModel
import com.example.cakeslist.core.common.viewBinding
import com.example.cakeslist.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    val binding by viewBinding<FragmentHomeBinding>()
    private val viewModel by viewModel<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCakes()
    }
}
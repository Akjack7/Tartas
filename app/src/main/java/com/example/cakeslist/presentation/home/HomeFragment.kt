package com.example.cakeslist.presentation.home

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.cakeslist.R
import com.example.cakeslist.core.common.viewBinding
import com.example.cakeslist.databinding.FragmentHomeBinding
import com.example.cakeslist.presentation.models.LoadingState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(R.layout.fragment_home), HomeListAdapter.CakeAdapterListener {

    private val binding by viewBinding<FragmentHomeBinding>()
    private val viewModel by viewModel<HomeViewModel>()

    private val mAdapter: HomeListAdapter by lazy {
        HomeListAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachObservers()
        setupList()
        viewModel.getCakes()
    }

    private fun attachObservers() {
        viewModel.cakesList.observe(viewLifecycleOwner, {
            setAnimation()
            mAdapter.items = it
            mAdapter.notifyDataSetChanged()
            binding.swipeCakesList.isRefreshing = false
        })
        viewModel.loadingState.observe(viewLifecycleOwner, { loadingState ->
            when (loadingState.status) {
                LoadingState.Status.RUNNING -> showLoading(true)
                LoadingState.Status.SUCCESS -> showLoading(false)
                LoadingState.Status.FAILED -> showLoading(false).also {
                    showEmpty(
                        loadingState.msg ?: getString(R.string.generic_error)
                    )
                }
            }
        })
    }

    private fun setAnimation() {
        val animationController =
            AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation_fall_down)
        binding.cakesList.layoutAnimation = animationController
    }

    private fun showEmpty(error: String) {
        with(binding) {
            errorGroup.isVisible = true
            errorText.text = error
            retryBtn.setOnClickListener { viewModel.getCakes() }
        }
    }

    private fun showLoading(show: Boolean) {
        with(binding) {
            errorGroup.isVisible = false
            progressBar.isVisible = show
            cakesList.isVisible = !show
            swipeCakesList.isRefreshing = false
        }
    }

    private fun setupList() {
        // todo, endlesscrolling for data, and pagination
        // todo, recyclerview adapter for diferent items, for example with this library : implementation 'com.hannesdorfmann:adapterdelegates4:4.3.0'
        binding.cakesList.apply {
            adapter = mAdapter
        }
        binding.swipeCakesList.setOnRefreshListener { viewModel.getCakes() }
    }

    override fun onClickCake(description: String) {
        setDetailAlert(description)
    }

    private fun setDetailAlert(msg: String) {
        //todo  move this to custom component
        MaterialAlertDialogBuilder(requireContext()).setTitle(getString(R.string.description))
            .setMessage(msg).setPositiveButton(
                getString(
                    R.string.accept
                )
            ) { dialog, _ -> dialog.dismiss() }.show()
    }
}
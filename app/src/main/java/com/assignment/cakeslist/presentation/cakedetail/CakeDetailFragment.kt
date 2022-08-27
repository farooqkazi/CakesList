package com.assignment.cakeslist.presentation.cakedetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.assignment.cakeslist.R
import com.assignment.cakeslist.data.entity.Cake
import com.assignment.cakeslist.databinding.FragmentCakeDetailBinding
import com.assignment.cakeslist.presentation.common.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CakeDetailFragment : Fragment(R.layout.fragment_cake_detail) {
    private val binding by viewBinding<FragmentCakeDetailBinding>()
    private val viewModel by viewModels<CakeDetailViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeUIState()
    }

    private fun initViews() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeUIState() = lifecycleScope.launch {
        viewModel.cake.flowWithLifecycle(lifecycle).collect(::updateUi)
    }

    private fun updateUi(cake: Cake) {
        Glide.with(this).load(cake.image).error(R.drawable.image_placeholder)
            .into(binding.thumbnail)
        binding.textTitle.text = cake.title
        binding.toolbar.title = cake.title
        binding.textDescription.text = cake.desc
    }
}
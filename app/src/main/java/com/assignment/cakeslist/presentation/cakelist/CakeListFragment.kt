package com.assignment.cakeslist.presentation.cakelist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.cakeslist.R
import com.assignment.cakeslist.data.entity.Cake
import com.assignment.cakeslist.databinding.FragmentCakeListBinding
import com.assignment.cakeslist.presentation.common.EmptyStatePresenter
import com.assignment.cakeslist.presentation.common.snackRetry
import com.assignment.cakeslist.presentation.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CakeListFragment : Fragment(R.layout.fragment_cake_list), (Cake) -> Unit,
    EmptyStatePresenter {
    override fun getEmptyStateLayout() = binding.emptyLayout
    private val binding by viewBinding<FragmentCakeListBinding>()
    private val viewModel by viewModels<CakeListViewModel>()
    private lateinit var adapter: CakeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = CakeAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        binding.recyclerView.setHasFixedSize(true)
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadCakes()
        }
        observeUIState()
    }

    private fun observeUIState() = lifecycleScope.launch {
        viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
    }

    private fun updateUI(uiState: CakeListViewModel.UiState) {
        when (uiState) {
            is CakeListViewModel.UiState.Error -> {
                binding.swipeRefreshLayout.isRefreshing = false
                if (adapter.itemCount > 0) {
                    snackRetry(
                        "No Internet connection! Please try again.",
                        blk = viewModel::loadCakes
                    )
                } else {
                    showNetworkError(viewModel::loadCakes)
                }
            }
            is CakeListViewModel.UiState.Loading ->
                binding.swipeRefreshLayout.isRefreshing = true
            is CakeListViewModel.UiState.Success -> {
                binding.swipeRefreshLayout.isRefreshing = false
                if (uiState.cakes.isNotEmpty()) {
                    showContent()
                } else {
                    showEmptyStat("No Cakes data available right now!")
                }
                adapter.submitList(uiState.cakes)
            }

        }
    }

    override fun invoke(cake: Cake) {
        val direction = CakeListFragmentDirections.actionCakeListToDetail(cake)
        findNavController().navigate(direction)
    }


}
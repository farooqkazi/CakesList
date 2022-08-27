package com.assignment.cakeslist.presentation.common

import android.view.View
import com.assignment.cakeslist.databinding.LayoutEmptyStateBinding

interface EmptyStatePresenter {
    fun getEmptyStateLayout(): LayoutEmptyStateBinding
    fun showEmptyStat(msg: String) {
        val emptyStateLayout = getEmptyStateLayout()
        emptyStateLayout.root.visibility = View.VISIBLE
        emptyStateLayout.empty.visibility = View.VISIBLE
        emptyStateLayout.textEmptyState.text = msg
        emptyStateLayout.error.visibility = View.GONE
    }

    fun showNetworkError(onRetry: () -> Unit) {
        val emptyStateLayout = getEmptyStateLayout()
        emptyStateLayout.root.visibility = View.VISIBLE
        emptyStateLayout.empty.visibility = View.GONE
        emptyStateLayout.error.visibility = View.VISIBLE
        emptyStateLayout.buttonRetryEmptyState.setOnClickListener {
            showContent()
            onRetry()
        }
    }

    fun showContent() {
        val emptyStateLayout = getEmptyStateLayout()
        emptyStateLayout.root.visibility = View.GONE
    }
}

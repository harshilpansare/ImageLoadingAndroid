package com.sample.pagination

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sample.pagination.databinding.RecyclerLastItemBinding
import com.sample.pagination.entity.NetworkState

class LastItemHolder(private val binding: RecyclerLastItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(networkState: NetworkState) {
        binding.progressBar.visibility = if (networkState === NetworkState.LOADING) View.VISIBLE else View.GONE
        binding.endText.visibility = if (networkState !== NetworkState.LOADING) View.GONE else View.VISIBLE
        binding.executePendingBindings()
    }
}

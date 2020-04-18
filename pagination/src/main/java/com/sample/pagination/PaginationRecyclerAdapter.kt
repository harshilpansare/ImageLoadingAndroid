package com.sample.pagination

import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sample.pagination.entity.DiffCallback
import com.sample.pagination.entity.NetworkState
import com.sample.pagination.entity.PaginationEntity

const val TYPE_ITEM = 1
const val TYPE_END = 2

abstract class PaginationRecyclerAdapter<V : PaginationEntity, VH : RecyclerView.ViewHolder> : PagedListAdapter<V, VH>(
    DiffCallback<V>()
) {
    open var networkState: NetworkState = NetworkState.LOADING
        set(newState) {
            val previousState = this.networkState
            val wasPreviousStateLoading = previousState == NetworkState.LOADING
            field = newState
            val isNewStateLoading = newState == NetworkState.LOADING
            val stateChanged = previousState != newState
            val loadingRemoved = wasPreviousStateLoading && !isNewStateLoading
            val loadingAdded = !wasPreviousStateLoading && isNewStateLoading
            when {
                loadingRemoved -> notifyItemRemoved(itemCount)
                loadingAdded -> notifyItemInserted(itemCount)
                stateChanged -> notifyItemChanged(itemCount - 1)
            }
        }

    override fun getItemViewType(position: Int): Int {
        return when {
            networkState == NetworkState.LOADING && position == itemCount - 1 -> TYPE_END
            else -> TYPE_ITEM
        }
    }
}

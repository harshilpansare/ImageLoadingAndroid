package com.sample.pagination.entity

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.parcel.Parcelize

@Parcelize
open class PaginationEntity(val identifier: String?,
                            val updatedValue: String?): Parcelable

class DiffCallback<T : PaginationEntity> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.identifier?.equals(newItem.identifier) ?: false
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return if (oldItem.updatedValue == null && newItem.updatedValue == null)
            true
        else
            oldItem.updatedValue?.equals(newItem.updatedValue) ?: false
    }
}

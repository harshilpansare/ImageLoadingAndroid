package com.sample.imageloading.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions.centerCropTransform
import com.sample.glide2.Glide2
import com.sample.imageloading.R
import com.sample.imageloading.databinding.RecyclerItemBinding
import com.sample.imageloading.domain.entity.Photo
import com.sample.pagination.LastItemHolder
import com.sample.pagination.PaginationRecyclerAdapter
import com.sample.pagination.TYPE_ITEM

class ImageRecyclerAdapter(context: Context, private val withGlide: Boolean) :
    PaginationRecyclerAdapter<Photo, RecyclerView.ViewHolder>() {

    private val glide2 = Glide2(context.applicationContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_ITEM)
            return ImageHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.recycler_item, parent, false
                )
            )
        else
            return LastItemHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.recycler_last_item, parent, false
                )
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_ITEM)
            (holder as ImageHolder).bind(getItem(position) ?: return, position)
        else
            (holder as LastItemHolder).bind(networkState)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is ImageHolder)
            holder.recycle()
    }

    fun clearScopedCache() {
        glide2.clearCache()
    }

    inner class ImageHolder(private val binding: RecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Photo, position: Int) {
            val imageUrl = photo.imageUrl
            if (withGlide)
                Glide.with(binding.image.context)
                    .load(imageUrl)
                    .apply(
                        centerCropTransform()
                            .placeholder(R.drawable.ic_placeholder)
                            .error(R.drawable.ic_placeholder)
                            .priority(Priority.HIGH)
                    )
                    .into(binding.image)
            else
                glide2.loadImage(
                    binding.image,
                    imageUrl,
                    R.drawable.ic_placeholder
                ) { notifyItemChanged(position) }
            binding.text.text = photo.title
        }

        fun recycle() {
            if (withGlide)
                Glide.with(itemView.context).clear(binding.image)
        }
    }
}
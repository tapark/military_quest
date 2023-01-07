package com.tapark.military_quest.account.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.tapark.military_quest.R
import com.tapark.military_quest.databinding.ItemImageSelectBinding

class ImageSelectAdapter(val onSelectItem: (Uri) -> Unit): ListAdapter<Uri, ImageSelectAdapter.ImageSelectViewHolder>(diffUtil) {

    inner class ImageSelectViewHolder(val binding: ItemImageSelectBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(uri: Uri, position: Int) {
            when (position % 3) {
                0 -> { binding.itemImageView.setPadding(0, 0, 4, 8) }
                1 -> { binding.itemImageView.setPadding(4, 0, 4, 8) }
                2 -> { binding.itemImageView.setPadding(4, 0, 0, 8) }
            }

            Glide.with(binding.itemImageView)
                .load(uri)
                .placeholder(R.color.white_5)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.color.white_5)
                .into(binding.itemImageView)

            binding.itemImageView.setOnClickListener {
                onSelectItem(uri)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSelectViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemImageSelectBinding.inflate(layoutInflater, parent, false)
        return ImageSelectViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageSelectViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Uri>() {
            override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
                return oldItem == newItem
            }
        }
    }
}
package com.tapark.military_quest.account.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tapark.military_quest.databinding.ItemFolderSelectBinding

class FolderSelectAdapter(val onItemClicked: (String, Int) -> Unit): ListAdapter<String, FolderSelectAdapter.FolderSelectViewHolder>(diffUtil) {

    inner class FolderSelectViewHolder(val binding: ItemFolderSelectBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(folder: String, position: Int) {
            binding.folderNameTextView.text = folder
            binding.root.setOnClickListener {
                onItemClicked(folder, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderSelectViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemFolderSelectBinding.inflate(layoutInflater, parent, false)
        return FolderSelectViewHolder(view)
    }

    override fun onBindViewHolder(holder: FolderSelectViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }
}
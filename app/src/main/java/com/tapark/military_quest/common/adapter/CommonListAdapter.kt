package com.tapark.military_quest.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tapark.military_quest.databinding.ItemCommonBinding

class CommonListAdapter(val itemSelected: (String) -> Unit): ListAdapter<String, CommonListAdapter.ClassViewHolder>(diffUtil) {

    inner class ClassViewHolder(private val binding: ItemCommonBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.commonItemText.text = item
            binding.commonItemText.setOnClickListener {
                itemSelected(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemCommonBinding.inflate(layoutInflater, parent, false)
        return ClassViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        holder.bind(currentList[position])
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


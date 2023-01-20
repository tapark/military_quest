package com.tapark.military_quest.home.adapter

import android.content.Context
import android.graphics.Color
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tapark.military_quest.R
import com.tapark.military_quest.data.SubQuestInfo
import com.tapark.military_quest.databinding.ItemSubQuestBinding
import com.tapark.military_quest.utils.ItemMoveSimpleCallback
import com.tapark.military_quest.utils.PrefManager
import com.tapark.military_quest.utils.ymdToMilli
import java.util.*

class SubQuestAdapter(val onEdit: (Int) -> Unit): RecyclerView.Adapter<SubQuestAdapter.SubQuestViewHolder>(),
ItemMoveSimpleCallback.ItemTouchHelperContract {

    private val subQuestList = mutableListOf<SubQuestInfo>()
    private val timerList = mutableListOf<CountDownTimer>()

    inner class SubQuestViewHolder(val binding: ItemSubQuestBinding, val context: Context): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SubQuestInfo, position: Int) {

            val startMilli = ymdToMilli(item.startDate)
            val endMilli = ymdToMilli(item.endDate)
            val currentMilli = System.currentTimeMillis()
            val dDay = (endMilli - currentMilli) / (24 * 60 * 60 * 1000) * -1
            val operator = if (dDay > 0) "+" else ""

            val currentPercent = (currentMilli - startMilli).toFloat() / (endMilli - startMilli).toFloat() * 100
            val leftPercent = 100 - currentPercent

            binding.progressTitleText.text = item.name
            binding.dDayText.text = context.getString(R.string.d_day_form, operator, dDay.toInt())
            binding.endDateText.text = item.endDate
            binding.subProgressView.progress = currentPercent
            binding.subProgressView.highlightView.color = Color.parseColor("#FFFFFFFF")

            val timer = object : CountDownTimer(endMilli - currentMilli, 100) {
                override fun onTick(p0: Long) {
                    val percent = (endMilli - currentMilli - p0).toDouble() / (endMilli - currentMilli).toDouble() * leftPercent + currentPercent
                    binding.progressText.text = String.format("%.7f", percent)
                }
                override fun onFinish() {
                    binding.progressText.text = "100"
                }
            }
            timerList.add(position, timer)
            timer.start()

            binding.editButton.setOnClickListener {
                onEdit(absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubQuestViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemSubQuestBinding.inflate(layoutInflater, parent, false)
        return SubQuestViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: SubQuestViewHolder, position: Int) {
        holder.bind(subQuestList[position], position)
    }

    override fun getItemCount(): Int = subQuestList.size

    fun initList(init: MutableList<SubQuestInfo>) {
        subQuestList.clear()
        subQuestList.addAll(init)
        notifyDataSetChanged()
    }

    fun updateList(position: Int) {
        subQuestList.clear()
        timerList[position].cancel()
        timerList.removeAt(position)
        subQuestList.addAll(PrefManager.getSubQuestList())
        notifyItemChanged(position)
    }

    fun addItemLast() {
        subQuestList.clear()
        subQuestList.addAll(PrefManager.getSubQuestList())
        notifyItemInserted(subQuestList.size - 1)
    }

    fun deleteItem(position: Int) {
        Log.d("박태규", "deleteItem : $position")
        subQuestList.clear()
        timerList[position].cancel()
        timerList.removeAt(position)
        subQuestList.addAll(PrefManager.getSubQuestList())
        notifyItemRemoved(position)
    }

    fun onDestroy() {
        timerList.forEach {
            it.cancel()
        }
        subQuestList.clear()
    }

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(subQuestList, i, i + 1)
                Collections.swap(timerList, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(subQuestList, i, i - 1)
                Collections.swap(timerList, i, i - 1)
            }
        }
        PrefManager.setSubQuestList(subQuestList)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onRowSelected(holder: RecyclerView.ViewHolder) {

    }
    override fun onRowClear(holder: RecyclerView.ViewHolder) {

    }

}
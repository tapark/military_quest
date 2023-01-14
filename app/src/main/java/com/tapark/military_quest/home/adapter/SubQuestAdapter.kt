package com.tapark.military_quest.home.adapter

import android.content.Context
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tapark.military_quest.R
import com.tapark.military_quest.data.SubQuestInfo
import com.tapark.military_quest.databinding.ItemSubQuestBinding
import com.tapark.military_quest.utils.ymdToMilli

class SubQuestAdapter: RecyclerView.Adapter<SubQuestAdapter.SubQuestViewHolder>() {

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

            val timer = object : CountDownTimer(endMilli - currentMilli, 100) {
                override fun onTick(p0: Long) {
                    val percent = (endMilli - currentMilli - p0).toDouble() / (endMilli - currentMilli).toDouble() * leftPercent + currentPercent
                    binding.progressText.text = String.format("%.7f", percent)
                }
                override fun onFinish() {
                    binding.progressText.text = "100"
                }
            }
            timerList.add(timer)
            timer.start()
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

    fun onDestroy() {
        timerList.forEach {
            it.cancel()
        }
        subQuestList.clear()
    }

}
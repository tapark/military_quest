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
import com.tapark.military_quest.databinding.ItemColorBinding
import com.tapark.military_quest.databinding.ItemSubQuestBinding
import com.tapark.military_quest.utils.ItemMoveSimpleCallback
import com.tapark.military_quest.utils.PrefManager
import com.tapark.military_quest.utils.ymdToMilli
import java.util.*

class ColorAdapter(val onClicked: (Int) -> Unit): RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    private val colorList = mutableListOf<Int>(
        Color.parseColor("#b2c7d9"), Color.parseColor("#677bac"), Color.parseColor("#9dcdb8"),
        Color.parseColor("#7fbdae"), Color.parseColor("#9bb156"), Color.parseColor("#f8cd59"),
        Color.parseColor("#f99460"), Color.parseColor("#5a4d4a"), Color.parseColor("#404372"),
        Color.parseColor("#f68181"), Color.parseColor("#d3d5d0"), Color.parseColor("#535252"),
        Color.parseColor("#f7a2bd"), Color.parseColor("#818b9c"), Color.parseColor("#10374a"),

        Color.parseColor("#e7708d"), Color.parseColor("#403464"), Color.parseColor("#d95c55"),
        Color.parseColor("#4fa16e"), Color.parseColor("#383838"), Color.parseColor("#fbf7dd"),
        Color.parseColor("#434343"), Color.parseColor("#57a5d6"), Color.parseColor("#a0a0a0"),
        Color.parseColor("#dbf0e9"), Color.parseColor("#5cc0cb"), Color.parseColor("#222222"),
        Color.parseColor("#2e3441"), Color.parseColor("#f4a09c"), Color.parseColor("#a3d1ce"),

        Color.parseColor("#f9f7f5"), Color.parseColor("#fdd801"), Color.parseColor("#31a555"),
        Color.parseColor("#c7c6c5"), Color.parseColor("#633b14"), Color.parseColor("#c59c6d"),
        Color.parseColor("#a23533"), Color.parseColor("#cc4c4a"), Color.parseColor("#cc6666"),
        Color.parseColor("#ff6666"), Color.parseColor("#ff9999"), Color.parseColor("#ffcccc"),
        Color.parseColor("#cc9999"), Color.parseColor("#993333"), Color.parseColor("#996666"),

        Color.parseColor("#839c98"), Color.parseColor("#f0b589"), Color.parseColor("#8dbdae"),
        Color.parseColor("#c6b5cf"), Color.parseColor("#a2478e"), Color.parseColor("#94bb43"),
        Color.parseColor("#4c484b"), Color.parseColor("#354560"), Color.parseColor("#b86613"),
        Color.parseColor("#223922"), Color.parseColor("#ffffff"), Color.parseColor("#000000"),
    )

    inner class ColorViewHolder(val binding: ItemColorBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(color: Int) {
            binding.colorCardView.setCardBackgroundColor(color)

            binding.root.setOnClickListener {
                onClicked(color)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemColorBinding.inflate(layoutInflater, parent, false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(colorList[position])
    }

    override fun getItemCount(): Int = colorList.size
}
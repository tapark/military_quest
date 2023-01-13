package com.tapark.military_quest.home.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tapark.military_quest.dpToPx

class SubQuestsItemDecoration: RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        when (position % 2) {
            0 -> {
                outRect.top = dpToPx(parent.context, 4)
                outRect.right = dpToPx(parent.context, 2)
            }
            1 -> {
                outRect.top = dpToPx(parent.context, 4)
                outRect.left = dpToPx(parent.context, 2)
            }
        }
    }

}
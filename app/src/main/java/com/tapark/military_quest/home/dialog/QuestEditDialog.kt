package com.tapark.military_quest.home.dialog

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.tapark.military_quest.R
import com.tapark.military_quest.databinding.DialogClassPickerBinding
import com.tapark.military_quest.databinding.DialogQuestEditBinding

class QuestEditDialog: DialogFragment() {

    private lateinit var viewDataBinding: DialogQuestEditBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_quest_edit, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSize()

    }

    private fun initSize() {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        activity?.let {
            val windowWidth = Resources.getSystem().displayMetrics.widthPixels
            val windowHeight = Resources.getSystem().displayMetrics.heightPixels
            viewDataBinding.root.layoutParams.apply {
                width = (windowWidth * 0.9F).toInt()
            }
        }
    }
}
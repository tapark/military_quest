package com.tapark.military_quest.common

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.tapark.military_quest.R
import com.tapark.military_quest.databinding.DialogRewardAdBinding

class RewardAdDialog: DialogFragment() {

    private lateinit var viewDataBinding: DialogRewardAdBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_reward_ad, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSize()
        onClicked()
    }

    private fun onClicked() {
        viewDataBinding.cancelButton.setOnClickListener {
            dismiss()

        }
        viewDataBinding.watchVideoButton.setOnClickListener {
            dismiss()

        }
    }

    private fun stepNext() {

    }

    private fun initSize() {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        activity?.let {
            val windowWidth = Resources.getSystem().displayMetrics.widthPixels
            val windowHeight = Resources.getSystem().displayMetrics.heightPixels
            viewDataBinding.root.layoutParams.apply {
                width = (windowWidth * 0.8F).toInt()
            }
        }
    }

}
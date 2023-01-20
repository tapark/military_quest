package com.tapark.military_quest.common

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.tapark.military_quest.R
import com.tapark.military_quest.data.SubQuestInfo
import com.tapark.military_quest.databinding.DialogConfirmBinding

class ConfirmDialog(
    private val message: String,
    private val cancel: String,
    private val confirm: String,
    val onConfirm: () -> Unit
    ): DialogFragment() {

    private lateinit var viewDataBinding: DialogConfirmBinding

    private lateinit var subQuestList: MutableList<SubQuestInfo>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_confirm, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSize()
        initData()
        onClicked()
    }

    private fun initData() {
        viewDataBinding.messageText.text = message
        viewDataBinding.cancelText.text = cancel
        viewDataBinding.confirmText.text = confirm
    }

    private fun onClicked() {
        viewDataBinding.cancelText.setOnClickListener { dismiss() }
        viewDataBinding.confirmText.setOnClickListener {
            dismiss()
            onConfirm()
        }
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
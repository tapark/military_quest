package com.tapark.military_quest.home.dialog

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.tapark.military_quest.R
import com.tapark.military_quest.common.ConfirmDialog
import com.tapark.military_quest.common.DatePickerDialog
import com.tapark.military_quest.data.SubQuestInfo
import com.tapark.military_quest.databinding.DialogClassPickerBinding
import com.tapark.military_quest.databinding.DialogQuestEditBinding
import com.tapark.military_quest.utils.PrefManager
import com.tapark.military_quest.utils.getAddedDate
import com.tapark.military_quest.utils.milliToYmd
import com.tapark.military_quest.utils.ymdToMilli

/**
*
* @param position -1 인 경우 Quest 추가 (add) 모드로 판단한다.
* */

class QuestEditDialog(private val position: Int, val onDelete: () -> Unit, val onUpdate: () -> Unit): DialogFragment() {

    private lateinit var viewDataBinding: DialogQuestEditBinding

    private lateinit var subQuestList: MutableList<SubQuestInfo>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_quest_edit, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSize()
        initData()
        onClicked()
    }

    private fun initData() {

        if (position < 0) {
            viewDataBinding.dialogTitleText.text = getString(R.string.common_add_mode)
            viewDataBinding.deleteButton.visibility = View.GONE
        } else {
            viewDataBinding.dialogTitleText.text = getString(R.string.common_modify_mode)
            viewDataBinding.deleteButton.visibility = View.VISIBLE
        }

        subQuestList = PrefManager.getSubQuestList()
        val data = if (position < 0) {
            val currentDate = milliToYmd(System.currentTimeMillis())
            SubQuestInfo("", currentDate, getAddedDate(currentDate, day = 100))
        } else {
            subQuestList[position]
        }

        val startMilli = ymdToMilli(data.startDate)
        val endMilli = ymdToMilli(data.endDate)
        val currentMilli = System.currentTimeMillis()
        val dDay = (endMilli - currentMilli) / (24 * 60 * 60 * 1000) * -1
        val operator = if (dDay > 0) "+" else ""

        val currentPercent = (currentMilli - startMilli).toFloat() / (endMilli - startMilli).toFloat() * 100

        viewDataBinding.apply {
            progressTitleText.text = data.name
            progressDDayText.text = getString(R.string.d_day_form, operator, dDay.toInt())
            progressEndDateText.text = data.endDate
            questProgressView.progress = currentPercent
            progressPercentText.text = currentPercent.toString()

            nameTitle.text = data.name
            startDateText.text = data.startDate
            endDateText.text = data.endDate
        }
    }

    private fun onClicked() {
        viewDataBinding.closeButton.setOnClickListener {
            dismiss()
        }

        viewDataBinding.nameEditText.addTextChangedListener {
            viewDataBinding.progressTitleText.text = it.toString()
        }
        viewDataBinding.startDateText.setOnClickListener {
            DatePickerDialog(viewDataBinding.startDateText.text.toString()) {
                viewDataBinding.startDateText.text = it
                initPercentage(it, viewDataBinding.endDateText.text.toString())
            }.show(parentFragmentManager, null)
        }
        viewDataBinding.endDateText.setOnClickListener {
            DatePickerDialog(viewDataBinding.endDateText.text.toString()) {
                viewDataBinding.endDateText.text = it
                initPercentage(viewDataBinding.startDateText.text.toString(), it)
            }.show(parentFragmentManager, null)
        }

        viewDataBinding.saveButton.setOnClickListener {

            if (position < 0) {
                subQuestList.add(
                    SubQuestInfo(
                        name = viewDataBinding.nameEditText.text.toString(),
                        startDate = viewDataBinding.startDateText.text.toString(),
                        endDate = viewDataBinding.endDateText.text.toString()
                    )
                )
            } else {
                subQuestList[position].apply {
                    name = viewDataBinding.nameEditText.text.toString()
                    startDate = viewDataBinding.startDateText.text.toString()
                    endDate = viewDataBinding.endDateText.text.toString()
                }
            }
            PrefManager.setSubQuestList(subQuestList)
            onUpdate()
            dismiss()
        }

        viewDataBinding.deleteButton.setOnClickListener {
            ConfirmDialog(
                message = getString(R.string.message_delete),
                cancel = getString(R.string.common_cancel),
                confirm = getString(R.string.common_delete),
                onConfirm = {
                    subQuestList.removeAt(position)
                    PrefManager.setSubQuestList(subQuestList)
                    onDelete()
                    dismiss()
                }
            ).show(parentFragmentManager, null)
        }
    }

    private fun initPercentage(startDate: String, endDate: String) {
        val startMilli = ymdToMilli(startDate)
        val endMilli = ymdToMilli(endDate)
        val currentMilli = System.currentTimeMillis()
        val currentPercent = (currentMilli - startMilli).toFloat() / (endMilli - startMilli).toFloat() * 100
        viewDataBinding.progressPercentText.text = currentPercent.toString()
        viewDataBinding.questProgressView.progress = currentPercent
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
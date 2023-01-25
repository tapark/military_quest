package com.tapark.military_quest.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.tapark.military_quest.R
import com.tapark.military_quest.databinding.DialogDatePickerBinding
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*

class DatePickerDialog(private val date: String, val onConfirm: (String) -> Unit): DialogFragment() {

    private lateinit var viewDataBinding: DialogDatePickerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_date_picker, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        initDate()
        onClicked()
    }

    private fun initDate() {
        val dateList = date.split(".")
        viewDataBinding.datePicker.updateDate(
            dateList[0].toInt(),
            dateList[1].toInt() - 1,
            dateList[2].toInt()
        )
    }

    private fun onClicked() {
        viewDataBinding.cancelButton.setOnClickListener {
            dialog?.dismiss()
        }
        viewDataBinding.confirmButton.setOnClickListener {

            val year = viewDataBinding.datePicker.year
            val month = viewDataBinding.datePicker.month + 1
            val day = viewDataBinding.datePicker.dayOfMonth


            onConfirm("${String.format("%04d", year)}.${String.format("%02d", month)}.${String.format("%02d", day)}")
            dialog?.dismiss()
        }
    }
}
package com.tapark.military_quest.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.tapark.military_quest.R
import com.tapark.military_quest.databinding.DialogSubClassPickerBinding

class SubClassPickerDialog: DialogFragment() {

    private lateinit var viewDataBinding: DialogSubClassPickerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_sub_class_picker, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)


    }

}
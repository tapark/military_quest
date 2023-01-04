package com.tapark.military_quest.common

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.tapark.military_quest.R
import com.tapark.military_quest.common.adapter.CommonListAdapter
import com.tapark.military_quest.databinding.DialogCompanyPickerBinding

class CompanyPickerDialog(val onClicked : (String) -> Unit): DialogFragment() {

    private lateinit var viewDataBinding: DialogCompanyPickerBinding

    private val activeList = mutableListOf<String>("육군", "해군", "공군", "해병", "의무경찰", "해양의무경찰", "의무소방")

    private val supplyList = mutableListOf<String>("사회복무요원", "산업기능요원(보충역)", "산업기능요원(현역)", "전문연구요원", "예술체육요원", "대체복무요원")

    private val reserveList = mutableListOf<String>("상근예비역", "승선근무예비역")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_company_picker, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSize()
        initAdapter()

    }

    private fun initAdapter() {

        FlexboxLayoutManager(requireContext()).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }.let {
            viewDataBinding.activeRecyclerView.layoutManager = it
        }

        FlexboxLayoutManager(requireContext()).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }.let {
            viewDataBinding.supplyRecyclerView.layoutManager = it
        }

        FlexboxLayoutManager(requireContext()).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }.let {
            viewDataBinding.reserveRecyclerView.layoutManager = it
        }

        viewDataBinding.activeRecyclerView.adapter = CommonListAdapter {
            onClicked(it)
            dismiss()
        }.apply { submitList(activeList) }

        viewDataBinding.supplyRecyclerView.adapter = CommonListAdapter {
            onClicked(it)
            dismiss()
        }.apply { submitList(supplyList) }

        viewDataBinding.reserveRecyclerView.adapter = CommonListAdapter {
            onClicked(it)
            dismiss()
        }.apply { submitList(reserveList) }
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
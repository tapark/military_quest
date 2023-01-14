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
import com.tapark.military_quest.databinding.DialogClassPickerBinding

class ClassPickerDialog(val company: String = "육군", val onClicked : (String) -> Unit): DialogFragment() {

    private lateinit var viewDataBinding: DialogClassPickerBinding

    private val classList1 = mutableListOf<String>("이경", "일경", "상경", "수경") // 의경, 해양의무경찰
    private val classList2 = mutableListOf<String>("이방", "일방", "상방", "수방") // 의무소방
    private val classList3 = mutableListOf<String>("1호봉", "2호봉", "3호봉", "4호봉") // 기타

    private val soldierList = mutableListOf<String>("이병", "일병", "상병", "병장") // 육군, 해군, 공군, 해병, 카투사, 특전사
    private val officerList = mutableListOf<String>("소위", "중위", "대위") // 장교
    private val subOfficerList = mutableListOf<String>("하사", "중사", "상사") // 부사관

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_class_picker, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSize()
        initView()
        initAdapter()

    }

    private fun initView() {
        when (company) {
            "육군", "해군", "공군", "해병" -> {
                viewDataBinding.militaryLayout.visibility = View.VISIBLE
                viewDataBinding.etcLayout.visibility = View.GONE
                initAdapter()
            }
            "상근예비역" -> {
                viewDataBinding.militaryLayout.visibility = View.GONE
                viewDataBinding.etcLayout.visibility = View.VISIBLE
                initEtcAdapter(soldierList)
            }
            "의무경찰", "해양의무경찰" -> {
                viewDataBinding.militaryLayout.visibility = View.GONE
                viewDataBinding.etcLayout.visibility = View.VISIBLE
                initEtcAdapter(classList1)
            }
            "의무소방" -> {
                viewDataBinding.militaryLayout.visibility = View.GONE
                viewDataBinding.etcLayout.visibility = View.VISIBLE
                initEtcAdapter(classList2)
            }
            else -> {
                viewDataBinding.militaryLayout.visibility = View.GONE
                viewDataBinding.etcLayout.visibility = View.VISIBLE
                initEtcAdapter(classList3)
            }
        }
    }

    private fun initAdapter() {

        FlexboxLayoutManager(requireContext()).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }.let {
            viewDataBinding.soldierRecyclerView.layoutManager = it
        }

        FlexboxLayoutManager(requireContext()).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }.let {
            viewDataBinding.subOfficerRecyclerView.layoutManager = it
        }

        FlexboxLayoutManager(requireContext()).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }.let {
            viewDataBinding.officerRecyclerView.layoutManager = it
        }

        viewDataBinding.soldierRecyclerView.adapter = CommonListAdapter {
            onClicked(it)
            dismiss()
        }.apply { submitList(soldierList) }

        viewDataBinding.subOfficerRecyclerView.adapter = CommonListAdapter {
            onClicked(it)
            dismiss()
        }.apply { submitList(subOfficerList) }

        viewDataBinding.officerRecyclerView.adapter = CommonListAdapter {
            onClicked(it)
            dismiss()
        }.apply { submitList(officerList) }

    }

    private fun initEtcAdapter(list: MutableList<String>) {
        FlexboxLayoutManager(requireContext()).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }.let {
            viewDataBinding.etcRecyclerView.layoutManager = it
        }
        viewDataBinding.etcTextView.text = company
        viewDataBinding.etcRecyclerView.adapter = CommonListAdapter {
            onClicked(it)
            dismiss()
        }.apply { submitList(list) }
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
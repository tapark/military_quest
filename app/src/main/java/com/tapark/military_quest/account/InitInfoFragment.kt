package com.tapark.military_quest.account

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.tapark.military_quest.R
import com.tapark.military_quest.base.BaseFragment
import com.tapark.military_quest.common.CompanyPickerDialog
import com.tapark.military_quest.common.DatePickerDialog
import com.tapark.military_quest.databinding.FragmentInitInfoBinding
import com.tapark.military_quest.milliToYmd

class InitInfoFragment: BaseFragment<FragmentInitInfoBinding, InitInfoViewModel>() {
    override val viewModel by viewModels<InitInfoViewModel>()

    override val layout: Int = R.layout.fragment_init_info

    override fun onBackPressed() {

    }

    override fun addObserver() {

    }

    override fun initViews(savedInstanceState: Bundle?) {
        initData()
        onClicked()
    }

    private fun initData() {
        val currentTime = System.currentTimeMillis()
        viewModel.apply {
            birthDate.value = milliToYmd(currentTime - 694252944149)
            enterDate.value = milliToYmd(currentTime)
            retireDate.value = milliToYmd(currentTime + 63113904013)
        }
    }

    private fun onClicked() {

        viewModel.apply {

            viewDataBinding.birthDayText.setOnClickListener {
                DatePickerDialog(birthDate.value!!) {
                    birthDate.value = it
                }.show(parentFragmentManager, null)
            }
            viewDataBinding.enterDateText.setOnClickListener {
                DatePickerDialog(enterDate.value!!) {
                    enterDate.value = it
                }.show(parentFragmentManager, null)
            }
            viewDataBinding.retireDateText.setOnClickListener {
                DatePickerDialog(retireDate.value!!) {
                    retireDate.value = it
                }.show(parentFragmentManager, null)
            }

            viewDataBinding.companyText.setOnClickListener {
                CompanyPickerDialog {
                    viewDataBinding.companyText.text = it
                }.show(parentFragmentManager, null)
            }
        }
    }
}
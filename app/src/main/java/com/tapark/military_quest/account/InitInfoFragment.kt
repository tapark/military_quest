package com.tapark.military_quest.account

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import com.tapark.military_quest.MainActivity
import com.tapark.military_quest.R
import com.tapark.military_quest.utils.PrefManager
import com.tapark.military_quest.utils.PrefManager.KEY_USER_INFO
import com.tapark.military_quest.base.BaseFragment
import com.tapark.military_quest.common.ClassPickerDialog
import com.tapark.military_quest.common.CompanyPickerDialog
import com.tapark.military_quest.common.DatePickerDialog
import com.tapark.military_quest.data.Info
import com.tapark.military_quest.data.UserInfo
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

            companyText.value = "육군"
            rankText.value = "일병"
        }
        Log.d("박태규", "userInfo : ${PrefManager.getUserInfo()}")
        val userInfo = PrefManager.getUserInfo()
        userInfo.let {
            viewModel.apply {
                birthDate.value = it.birth.value
                enterDate.value = it.enter.value
                retireDate.value = it.retire.value

                nameText.value = it.name.value
                companyText.value = it.company.value
                rankText.value = it.rank.value

                isPrivateBirth.value = it.birth.private
                isPrivateEnterDate.value = it.enter.private
                isPrivateRetireDate.value = it.retire.private
                isPrivateName.value = it.name.private
                isPrivateCompany.value = it.company.private
                isPrivateClass.value = it.rank.private
            }
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
            viewDataBinding.classText.setOnClickListener {
                ClassPickerDialog(viewDataBinding.companyText.text.toString()) {
                    viewDataBinding.classText.text = it
                }.show(parentFragmentManager, null)
            }

            viewDataBinding.completeButton.setOnClickListener {

                val userInfo = UserInfo(
                    name = Info(viewDataBinding.nameEditText.text.toString(), viewModel.isPrivateName.value!!),
                    birth = Info(viewDataBinding.birthDayText.text.toString(), viewModel.isPrivateBirth.value!!),
                    company = Info(viewDataBinding.companyText.text.toString(), viewModel.isPrivateClass.value!!),
                    rank = Info(viewDataBinding.classText.text.toString(), viewModel.isPrivateClass.value!!),
                    enter = Info(viewDataBinding.enterDateText.text.toString(), viewModel.isPrivateEnterDate.value!!),
                    retire = Info(viewDataBinding.retireDateText.text.toString(), viewModel.isPrivateRetireDate.value!!),
                )

                PrefManager.setUserInfo(userInfo)
                (activity as MainActivity).showHomeFragment()
            }
        }
    }
}
package com.tapark.military_quest.home

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.skydoves.progressview.ProgressView
import com.tapark.military_quest.MainActivity
import com.tapark.military_quest.R
import com.tapark.military_quest.base.BaseFragment
import com.tapark.military_quest.data.UserInfo
import com.tapark.military_quest.databinding.FragmentHomeBinding
import com.tapark.military_quest.utils.PrefManager
import com.tapark.military_quest.utils.PrefManager.KEY_PROFILE_BITMAP
import com.tapark.military_quest.ymdToMilli
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel by viewModels<HomeViewModel>()
    override val layout: Int = R.layout.fragment_home

    lateinit var userInfo: UserInfo

    override fun onBackPressed() {
        activity?.finish()
    }

    override fun addObserver() {
        viewDataBinding.waveText.setOnClickListener {
            (activity as MainActivity).showInitInfoFragment()
        }
    }

    override fun initViews(savedInstanceState: Bundle?) {

        initData()
        onClick()
    }

    private fun onClick() {
        viewDataBinding.modifyInfoButton.setOnClickListener {
            (activity as MainActivity).showInitInfoFragment()
        }
    }

    private fun initData() {

        userInfo = PrefManager.getUserInfo()

        // profile data
        viewDataBinding.apply {
            subNameText.text = getString(R.string.sub_name_form, userInfo.company.value, userInfo.rank.value)
            nameText.text = userInfo.name.value.ifEmpty { "김너굴" }
            enterText.text = getString(R.string.enter_date_form, userInfo.enter.value)
            retireText.text = getString(R.string.retire_date_form, userInfo.retire.value)
            profileImageView.setImageBitmap(PrefManager.getBitmap(KEY_PROFILE_BITMAP))
        }

        // main quest
        initMainProgress(userInfo.enter.value, userInfo.retire.value)

    }

    private fun initMainProgress(startDate: String, endDate: String) {

        val startMilli = ymdToMilli(startDate)
        val endMilli = ymdToMilli(endDate)
        val currentMilli = System.currentTimeMillis()
        val dDay = (endMilli - currentMilli) / (24 * 60 * 60 * 1000)

        val currentPercent = (currentMilli - startMilli).toFloat() / (endMilli - startMilli).toFloat() * 100
        val leftPercent = 100 - currentPercent

        viewDataBinding.dDayText.text = getString(R.string.d_day_form, dDay.toInt())
        viewDataBinding.endDateText.text = endDate
        viewDataBinding.mainProgressView.progress = currentPercent

        object : CountDownTimer(endMilli - currentMilli, 100) {
            override fun onTick(p0: Long) {
                val percent = (endMilli - currentMilli - p0).toDouble() / (endMilli - currentMilli).toDouble() * leftPercent + currentPercent
                viewDataBinding.progressText.text = String.format("%.7f", percent)
            }
            override fun onFinish() {  }
        }.start()

    }

}
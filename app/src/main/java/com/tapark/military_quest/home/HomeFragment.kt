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
import com.tapark.military_quest.data.*
import com.tapark.military_quest.databinding.FragmentHomeBinding
import com.tapark.military_quest.home.adapter.SubQuestAdapter
import com.tapark.military_quest.home.adapter.SubQuestsItemDecoration
import com.tapark.military_quest.utils.PrefManager
import com.tapark.military_quest.utils.PrefManager.KEY_PROFILE_BITMAP
import com.tapark.military_quest.ymdToMilli
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel by viewModels<HomeViewModel>()
    override val layout: Int = R.layout.fragment_home

    lateinit var mainCountDownTimer: CountDownTimer

    lateinit var userInfo: UserInfo
    lateinit var subQuestAdapter: SubQuestAdapter

    lateinit var subQuestList: MutableList<SubQuestInfo>

    override fun onBackPressed() {
        activity?.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainCountDownTimer.cancel()
        subQuestAdapter.onDestroy()
    }

    override fun addObserver() {

    }

    override fun initViews(savedInstanceState: Bundle?) {

        initData()
        initAdapter()
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
        initMainProgress(userInfo)

        // subQuest

        subQuestList = PrefManager.getSubQuestList()
        if (subQuestList.isEmpty())
            initSubQuest(userInfo)
    }

    private fun initMainProgress(userInfo: UserInfo) {

        val startMilli = ymdToMilli(userInfo.enter.value)
        val endMilli = ymdToMilli(userInfo.retire.value)
        val currentMilli = System.currentTimeMillis()
        val dDay = (endMilli - currentMilli) / (24 * 60 * 60 * 1000) * -1
        val operator = if (dDay > 0) "+" else ""

        val currentPercent = (currentMilli - startMilli).toFloat() / (endMilli - startMilli).toFloat() * 100
        val leftPercent = 100 - currentPercent

        viewDataBinding.dDayText.text = getString(R.string.d_day_form, operator, dDay.toInt())
        viewDataBinding.endDateText.text = userInfo.retire.value
        viewDataBinding.mainProgressView.progress = currentPercent

        mainCountDownTimer = object : CountDownTimer(endMilli - currentMilli, 100) {
            override fun onTick(p0: Long) {
                val percent = (endMilli - currentMilli - p0).toDouble() / (endMilli - currentMilli).toDouble() * leftPercent + currentPercent
                viewDataBinding.progressText.text = String.format("%.7f", percent)
            }
            override fun onFinish() {
                viewDataBinding.progressText.text = "100"
            }
        }
        mainCountDownTimer.start()

    }

    private fun initSubQuest(userInfo: UserInfo) {
        val dataList = when (userInfo.company.value) {
            "육군", "해군", "공군", "해병", "상근예비역" -> {
                when (userInfo.rank.value) {
                    "이병", "일병", "상병", "병장" -> {
                        subQuest1
                    }
                    "하사", "중사", "상사", "원사" -> {
                        subQuest4
                    }
                    else -> {
                        subQuest5
                    }
                }
            }
            "의무경찰", "해양의무경찰" -> {
                subQuest2
            }
            "의무소방" -> {
                subQuest3
            }
            else -> {
                subQuest6
            }
        }

        dataList.forEach {data ->

            val enter = userInfo.enter.value
            val calendar = Calendar.getInstance()
            calendar.time = Date(ymdToMilli(enter))
            val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
            calendar.add(Calendar.MONTH, data.month)
            val info = SubQuestInfo(data.nextRank + " 진급", enter, dateFormat.format(calendar.time))
            subQuestList.add(info)
        }
        PrefManager.setSubQuestList(subQuestList)
    }

    private fun initAdapter() {

        subQuestAdapter = SubQuestAdapter()
        viewDataBinding.subQuestRecyclerView.adapter = subQuestAdapter
        viewDataBinding.subQuestRecyclerView.addItemDecoration(SubQuestsItemDecoration())
        subQuestAdapter.initList(subQuestList)
    }

}
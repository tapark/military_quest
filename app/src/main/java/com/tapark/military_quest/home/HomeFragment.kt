package com.tapark.military_quest.home

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.viewModels
import com.tapark.military_quest.R
import com.tapark.military_quest.base.BaseFragment
import com.tapark.military_quest.databinding.FragmentHomeBinding
import com.tapark.military_quest.utils.PrefManager
import com.tapark.military_quest.ymdToMilli
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel by viewModels<HomeViewModel>()
    override val layout: Int = R.layout.fragment_home


    override fun onBackPressed() {

    }

    override fun addObserver() {

    }

    override fun initViews(savedInstanceState: Bundle?) {
        val userInfo = PrefManager.getUserInfo()

        val enterSec = (ymdToMilli(userInfo.enter.value) / 1000).toInt()
        val retireSec = (ymdToMilli(userInfo.retire.value) / 1000).toInt()
        val currentSec = (System.currentTimeMillis() / 1000).toInt()

        viewDataBinding.backgroundWaveLayout.apply {
            max = retireSec - enterSec
            progress = currentSec - enterSec
        }

        val timeCurrent = System.currentTimeMillis() - ymdToMilli(userInfo.enter.value)
        val timeLeft = ymdToMilli(userInfo.retire.value) - System.currentTimeMillis()
        val timeTotal = ymdToMilli(userInfo.retire.value) - ymdToMilli(userInfo.enter.value)

        val currentPercent = timeCurrent.toDouble() / timeTotal.toDouble() * 100
        val leftPercent = timeLeft.toDouble() / timeTotal.toDouble() * 100

        Log.d("박태규", "$currentPercent / $leftPercent")

        val countDownTimer = object : CountDownTimer(timeLeft,100) {

            override fun onTick(p0: Long) {
//                Log.d("박태규", "tic : $p0 / $timeLeft")

                val percent = (timeLeft - p0).toDouble() / timeLeft.toDouble() * leftPercent + currentPercent
                viewDataBinding.waveText.text = String.format("%.7f", percent)
            }

            override fun onFinish() {

            }

        }.start()
    }
}
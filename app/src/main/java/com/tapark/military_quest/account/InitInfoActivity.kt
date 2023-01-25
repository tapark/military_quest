package com.tapark.military_quest.account

import android.os.Bundle
import androidx.activity.viewModels
import com.tapark.military_quest.R
import com.tapark.military_quest.base.BaseActivity
import com.tapark.military_quest.databinding.ActivityInitInfoBinding
import com.tapark.military_quest.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitInfoActivity : BaseActivity<ActivityInitInfoBinding, InitInfoActivityViewModel>() {
    override val viewModel by viewModels<InitInfoActivityViewModel>()
    override val layout = R.layout.activity_init_info

    override fun addObserver() {

    }

    override fun initViews(savedInstanceState: Bundle?) {

        showInitInfoFragment()
    }


    fun showInitInfoFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, InitInfoFragment()).addToBackStack(null).commitAllowingStateLoss()
    }

    fun showImageSelectFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, ImageSelectFragment()).addToBackStack(null).commitAllowingStateLoss()
    }

    fun showImageCropFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, ImageCropFragment()).addToBackStack(null).commitAllowingStateLoss()
    }

    fun removeFragment() {
        supportFragmentManager.popBackStack()
    }
}
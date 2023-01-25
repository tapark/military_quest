package com.tapark.military_quest.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.gms.ads.*
import com.tapark.military_quest.MainActivity
import com.tapark.military_quest.R
import com.tapark.military_quest.account.InitInfoActivity
import com.tapark.military_quest.base.BaseActivity
import com.tapark.military_quest.databinding.ActivityMainBinding
import com.tapark.military_quest.utils.PrefManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivityMainBinding, SplashActivityViewModel>() {
    override val viewModel by viewModels<SplashActivityViewModel>()
    override val layout = R.layout.activity_splash

    override fun addObserver() {

    }

    override fun initViews(savedInstanceState: Bundle?) {


        if (PrefManager.getUserInfo().firstInit) {
            val intent = Intent(this, InitInfoActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
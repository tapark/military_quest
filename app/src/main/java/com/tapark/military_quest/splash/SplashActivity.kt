package com.tapark.military_quest.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.android.gms.ads.*
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.tapark.military_quest.BuildConfig
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

    private lateinit var appUpdateManager: AppUpdateManager
    override fun addObserver() {

    }

    override fun onResume() {
        //현재 업데이트 중일 경우 업데이트 계속 수행
        appUpdateManager.appUpdateInfo.addOnSuccessListener {
            if(it.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS){
                appUpdateManager.startUpdateFlowForResult(
                    it,
                    AppUpdateType.IMMEDIATE,
                    this, REQUEST_APP_UPDATE_CODE
                )
            }
        }
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_APP_UPDATE_CODE) {
            when(resultCode){
                Activity.RESULT_OK ->{
                    // 앱 업데이트 완료로 인한 재실행
                    appUpdateManager.completeUpdate()
                }
                else ->{
                    this.finish()
                }
            }
        }
    }
    override fun initViews(savedInstanceState: Bundle?) {

        appUpdateManager = AppUpdateManagerFactory.create(this) // in-app-update
        checkAppVersion()
    }

    private fun checkAppVersion() {
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        var appVersion = remoteConfig.getString("app_version")
        Log.d("박태규", "appVersion : $appVersion")

        remoteConfig.fetchAndActivate().addOnCompleteListener(this) {task ->
            if (task.isSuccessful) {
                if (appVersion.replace(".", "").toInt() <= BuildConfig.VERSION_NAME.replace(".", "").toInt()) {
                    moveNeXtActivity()
                } else {
                    inAppUpdate()
                }
            } else {
                moveNeXtActivity()
            }
        }
    }

    private fun moveNeXtActivity() {
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

    private fun inAppUpdate() {
        Log.d("박태규", "inAppUpdate")
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            Log.d("박태규", "inAppUpdate - updateAvailability : ${appUpdateInfo.updateAvailability()} / ${appUpdateInfo.isUpdateTypeAllowed(
                AppUpdateType.IMMEDIATE)}")
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                Log.d("박태규", "inAppUpdate - updateAvailability true")
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
                    this, REQUEST_APP_UPDATE_CODE
                )
            } else {
                Log.d("박태규", "inAppUpdate - showDialog")

            }
        }
    }
    companion object {
        const val REQUEST_APP_UPDATE_CODE = 1022
    }
}
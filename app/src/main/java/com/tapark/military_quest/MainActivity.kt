package com.tapark.military_quest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.activity.viewModels
import com.google.android.gms.ads.*
import com.tapark.military_quest.account.InitInfoFragment
import com.tapark.military_quest.base.BaseActivity
import com.tapark.military_quest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {
    override val viewModel by viewModels<MainActivityViewModel>()
    override val layout = R.layout.activity_main

    private var initialLayoutComplete = false

    private lateinit var adView: AdView
    private val adSize: AdSize
        get() {
//            val display = windowManager.defaultDisplay
//            val outMetrics = DisplayMetrics()
//            display.getMetrics(outMetrics)
//
//            val density = outMetrics.density
//
//            var adWidthPixels = viewDataBinding.adViewContainer.width.toFloat()
//            if (adWidthPixels == 0f) {
//                adWidthPixels = outMetrics.widthPixels.toFloat()
//            }

//            val adWidth = getScreenWidth(this)


            val display = windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val density = outMetrics.density

            var adWidthPixels = viewDataBinding.adViewContainer.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()

            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
        }

    override fun onPause() {
        adView.pause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        adView.resume()
    }

    override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }

    override fun addObserver() {

    }

    override fun initViews(savedInstanceState: Bundle?) {

        Log.d("박태규", "Google Mobile Ads SDK Version: " + MobileAds.getVersion())
        initAdMob()

        showInitInfoFragment()
    }

    private fun initAdMob() {
        MobileAds.initialize(this) {}
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(listOf("ABCDEF012345")).build()
        )
        adView = AdView(this)
        viewDataBinding.adViewContainer.addView(adView)

        viewDataBinding.adViewContainer.viewTreeObserver.addOnGlobalLayoutListener {
            if (!initialLayoutComplete) {
                initialLayoutComplete = true
                loadBottomBanner()
            }
            adView.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    Log.d("박태규", "initAdMob - onAdLoaded")
                    super.onAdLoaded()
//                    if (!initSplashComplete) {
//                        initSplash()
//                        initSplashComplete = true
//                    }
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    Log.d("박태규", "onAdFailedToLoad - $p0")
                    super.onAdFailedToLoad(p0)
                }

                override fun onAdOpened() {
                    Log.d("박태규", "initAdMob - onAdOpened")
                    super.onAdOpened()
                }
            }
        }
    }

    private fun loadBottomBanner() {
        adView.adUnitId = AD_UNIT_ID
        adView.setAdSize(adSize)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    fun showInitInfoFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, InitInfoFragment()).commitAllowingStateLoss()
    }



    companion object {
        // This is an ad unit ID for a test ad. Replace with your own banner ad unit ID.
        private const val AD_UNIT_ID = "ca-app-pub-3940256099942544/9214589741"
    }
}
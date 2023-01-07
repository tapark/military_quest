package com.tapark.military_quest.account

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.canhub.cropper.CropImageView
import com.tapark.military_quest.MainActivity
import com.tapark.military_quest.R
import com.tapark.military_quest.base.BaseFragment
import com.tapark.military_quest.data.UserInfo
import com.tapark.military_quest.databinding.FragmentImageCropBinding

class ImageCropFragment: BaseFragment<FragmentImageCropBinding, ImageCropViewModel>() {
    override val viewModel by viewModels<ImageCropViewModel>()
    override val layout: Int = R.layout.fragment_image_crop

    override fun onBackPressed() {
        (activity as MainActivity).removeFragment()
    }

    override fun addObserver() {

    }

    override fun initViews(savedInstanceState: Bundle?) {
        initData()
        onClicked()
    }

    private fun initData() {

        setFragmentResultListener("imageUri") { requestKey, bundle ->

            val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable("imageUri", Uri::class.java)
            } else {
                bundle.getParcelable("imageUri")
            }
            Log.d("박태규", "uri : $uri")
            viewDataBinding.cropImageView.setImageUriAsync(uri)
        }

    }

    private fun onClicked() {
        viewDataBinding.reSelectText.setOnClickListener {
            (activity as MainActivity).removeFragment()
        }

        viewDataBinding.saveButton.setOnClickListener {
            setFragmentResult("imageSource", bundleOf("imageSource" to viewDataBinding.cropImageView.getCroppedImage()))
            (activity as MainActivity).removeFragment()
            (activity as MainActivity).removeFragment()
        }
    }
}
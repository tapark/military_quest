package com.tapark.military_quest.account

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.tapark.military_quest.MainActivity
import com.tapark.military_quest.R
import com.tapark.military_quest.utils.PrefManager
import com.tapark.military_quest.base.BaseFragment
import com.tapark.military_quest.common.ClassPickerDialog
import com.tapark.military_quest.common.CompanyPickerDialog
import com.tapark.military_quest.common.DatePickerDialog
import com.tapark.military_quest.data.*
import com.tapark.military_quest.databinding.FragmentInitInfoBinding
import com.tapark.military_quest.utils.milliToYmd
import com.tapark.military_quest.utils.PrefManager.KEY_PROFILE_BITMAP
import com.tapark.military_quest.utils.getAddedDate

class InitInfoFragment: BaseFragment<FragmentInitInfoBinding, InitInfoViewModel>() {
    override val viewModel by viewModels<InitInfoViewModel>()

    override val layout: Int = R.layout.fragment_init_info

    private var permissionRequester = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all { it.value }
        Log.d("박태규", "permissions : $permissions / granted : $granted")
        if (granted) {
            (activity as MainActivity).showImageSelectFragment()
        } else {
            if (PERMISSIONS.all { shouldShowRequestPermissionRationale(it) }) {
                activity?.finish()
            } else {
                Toast.makeText(context, "설정화면에서 권한을 허용해주세요.", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    .setData(Uri.parse("package:${activity?.packageName}"))
                startActivity(intent)
                activity?.finish()
            }
        }
    }

    lateinit var userInfo: UserInfo
    var profileImage :Bitmap? = null
    var serviceMonth = 18

    override fun onBackPressed() {
        if (userInfo.firstInit) {
            activity?.finish()
        } else {
            (activity as MainActivity).removeFragment()
        }
    }

    override fun addObserver() {

    }

    override fun initViews(savedInstanceState: Bundle?) {
        initData()
        initFragmentListener()
        onClicked()
    }

    private fun initData() {
        profileImage = PrefManager.getBitmap(KEY_PROFILE_BITMAP)
        viewDataBinding.profileImageView.setImageBitmap(profileImage)
        userInfo = PrefManager.getUserInfo()

        if (userInfo.firstInit) {
            val currentTime = System.currentTimeMillis()
            viewModel.apply {
                birthDate.value = milliToYmd(currentTime - 694252944149)
                enterDate.value = milliToYmd(currentTime)
                retireDate.value = milliToYmd(currentTime + 63113904013)

                companyText.value = "육군"
                rankText.value = "일병"
            }
        } else {
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
    }

    private fun initFragmentListener() {
        setFragmentResultListener("imageSource") { requestKey, bundle ->
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable("imageSource", Bitmap::class.java)
            } else {
                bundle.getParcelable("imageSource")
            }
            Log.d("박태규", "bitmap: $bitmap")
            if (bitmap != null)
                profileImage = bitmap
            viewDataBinding.profileImageView.setImageBitmap(bitmap)
        }
    }

    private fun onClicked() {

        viewModel.apply {

            viewDataBinding.profileCardView.setOnClickListener {

                activity?.let {
                    if (hasPermissions(it, PERMISSIONS)) {
                        (activity as MainActivity).showImageSelectFragment()
                    } else {
                        permissionRequester.launch(PERMISSIONS)
                    }
                }
            }

            viewDataBinding.birthDayText.setOnClickListener {
                DatePickerDialog(birthDate.value!!) {
                    birthDate.value = it
                }.show(parentFragmentManager, null)
            }
            viewDataBinding.companyText.setOnClickListener {
                CompanyPickerDialog {
                    viewDataBinding.companyText.text = it
                    when (it) {
                        "육군", "해병", "의무경찰, 상근예비역"         -> { serviceMonth = MONTH_18 } // 18
                        "해군", "해양의무경찰", "의무소방"            -> { serviceMonth = MONTH_20 } // 20
                        "공군", "사회복무요원"                       -> { serviceMonth = MONTH_21 } // 21
                        "산업기능요원(보충역)"                       -> { serviceMonth = MONTH_23 } // 23
                        "산업기능요원(현역), 예술체육요원"             -> { serviceMonth = MONTH_34 } // 34
                        "전문연구요원", "대체복무요원", "승선근무예비역" -> { serviceMonth = MONTH_36 } // 36
                    }
                    retireDate.value = getAddedDate(enterDate.value!!, month = serviceMonth)
                }.show(parentFragmentManager, null)
            }
            viewDataBinding.classText.setOnClickListener {
                ClassPickerDialog(viewDataBinding.companyText.text.toString()) {
                    viewDataBinding.classText.text = it
                    when (it) {
                        "소위", "중위", "대위" -> { serviceMonth = MONTH_36 }
                        "하사", "중사", "상사" -> { serviceMonth = MONTH_48 }
                    }
                    retireDate.value = getAddedDate(enterDate.value!!, month = serviceMonth)
                }.show(parentFragmentManager, null)
            }
            viewDataBinding.enterDateText.setOnClickListener {
                DatePickerDialog(enterDate.value!!) {
                    enterDate.value = it
                    retireDate.value = getAddedDate(enterDate.value!!, month = serviceMonth)
                }.show(parentFragmentManager, null)
            }
            viewDataBinding.retireDateText.setOnClickListener {
                DatePickerDialog(retireDate.value!!) {
                    retireDate.value = it
                }.show(parentFragmentManager, null)
            }

            viewDataBinding.completeButton.setOnClickListener {

                val userInfo = UserInfo(
                    firstInit = false,
                    name = Info(viewDataBinding.nameEditText.text.toString(), viewModel.isPrivateName.value!!),
                    birth = Info(viewDataBinding.birthDayText.text.toString(), viewModel.isPrivateBirth.value!!),
                    company = Info(viewDataBinding.companyText.text.toString(), viewModel.isPrivateClass.value!!),
                    rank = Info(viewDataBinding.classText.text.toString(), viewModel.isPrivateClass.value!!),
                    enter = Info(viewDataBinding.enterDateText.text.toString(), viewModel.isPrivateEnterDate.value!!),
                    retire = Info(viewDataBinding.retireDateText.text.toString(), viewModel.isPrivateRetireDate.value!!),
                )

                PrefManager.setUserInfo(userInfo)
                if (profileImage != null) {
                    PrefManager.setBitmap(KEY_PROFILE_BITMAP, profileImage!!)
                }
                (activity as MainActivity).showHomeFragment()
            }
        }
    }


    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

    companion object {
        private const val READ_EXTERNAL_STORAGE = android.Manifest.permission.READ_EXTERNAL_STORAGE
        private const val WRITE_EXTERNAL_STORAGE = android.Manifest.permission.WRITE_EXTERNAL_STORAGE

        private val PERMISSIONS = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) arrayOf(READ_EXTERNAL_STORAGE)
        else arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)
    }
}
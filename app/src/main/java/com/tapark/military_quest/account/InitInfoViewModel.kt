package com.tapark.military_quest.account

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import com.tapark.military_quest.base.BaseViewModel

class InitInfoViewModel: BaseViewModel() {

    var isPrivateName = MutableLiveData<Boolean>(false)
    var isPrivateBirth = MutableLiveData<Boolean>(false)
    var isPrivateCompany = MutableLiveData<Boolean>(false)
    var isPrivateClass  = MutableLiveData<Boolean>(false)
    var isPrivateEnterDate = MutableLiveData<Boolean>(false)
    var isPrivateRetireDate = MutableLiveData<Boolean>(false)

    var nameText = MutableLiveData<String>("")
    var companyText = MutableLiveData<String>("")
    var rankText = MutableLiveData<String>("")

    var birthDate = MutableLiveData<String>("")
    var enterDate = MutableLiveData<String>("")
    var retireDate = MutableLiveData<String>("")


    fun onNameClicked() {
        isPrivateName.value = !isPrivateName.value!!
    }
    fun onBirthClicked() {
        isPrivateBirth.value = !isPrivateBirth.value!!
    }
    fun onCompanyClicked() {
        isPrivateCompany.value = !isPrivateCompany.value!!
    }
    fun onClassClicked() {
        isPrivateClass.value = !isPrivateClass.value!!
    }
    fun onEnterDateClicked() {
        isPrivateEnterDate.value = !isPrivateEnterDate.value!!
    }
    fun onRetireDateClicked() {
        isPrivateRetireDate.value = !isPrivateRetireDate.value!!
    }

}
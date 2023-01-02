package com.tapark.military_quest.account

import androidx.lifecycle.MutableLiveData
import com.tapark.military_quest.base.BaseViewModel

class InitInfoViewModel: BaseViewModel() {

    var isPrivateName = MutableLiveData<Boolean>(false)
    var isPrivateBirth = MutableLiveData<Boolean>(false)
    var isPrivateCompany = MutableLiveData<Boolean>(false)
    var isPrivateMainClass  = MutableLiveData<Boolean>(false)
    var isPrivateSubClass  = MutableLiveData<Boolean>(false)
    var isPrivateEnterDate = MutableLiveData<Boolean>(false)
    var isPrivateRetireDate = MutableLiveData<Boolean>(false)

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
    fun onMainClassClicked() {
        isPrivateMainClass.value = !isPrivateMainClass.value!!
    }
    fun onSubClassClicked() {
        isPrivateSubClass.value = !isPrivateSubClass.value!!
    }
    fun onEnterDateClicked() {
        isPrivateEnterDate.value = !isPrivateEnterDate.value!!
    }
    fun onRetireDateClicked() {
        isPrivateRetireDate.value = !isPrivateRetireDate.value!!
    }

}
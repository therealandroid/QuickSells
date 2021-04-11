package com.diogojayme.quicksells.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    var loadingLiveData = MutableLiveData<Boolean>()
    var registerLiveData = MutableLiveData<Boolean>()

    //Use case 001 (REGISTER)
    fun register(name: String, password: String, email: String) {
        loadingLiveData.postValue(true)

        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)

            if (name.isNullOrEmpty() || password.isNullOrEmpty() || email.isNullOrEmpty()) {
                registerLiveData.postValue(false)//erro cadastro
            } else {
                registerLiveData.postValue(true)
            }

            loadingLiveData.postValue(false)//remove loading
        }
    }
}
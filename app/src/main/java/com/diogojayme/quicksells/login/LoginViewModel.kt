package com.diogojayme.quicksells.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val fake_username = "diogo"
    private val fake_password = "123456"

    var loadingLiveData = MutableLiveData<Boolean>()
    var loginLiveData = MutableLiveData<Boolean>()

    //Use case 001 (LOGIN)
    fun login(username: String, password: String) {

        loadingLiveData.postValue(true)

        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            loadingLiveData.postValue(false)//remove loading

            if (username == fake_username && password == fake_password) {
                loginLiveData.postValue(true)
            } else {
                loginLiveData.postValue(false)
            }
        }

    }
}
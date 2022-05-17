package com.binar.mymovieview.ui.splashscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.binar.mymovieview.data.local.userauth.UserRepository


class SplashViewModel(private val repository: UserRepository): ViewModel() {

    val loginValidation : MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }


    fun result(): LiveData<Boolean> {
        return loginValidation
    }

    fun loginCheck(): LiveData<String> {
        return repository.getUsernameValue().asLiveData()
    }

}
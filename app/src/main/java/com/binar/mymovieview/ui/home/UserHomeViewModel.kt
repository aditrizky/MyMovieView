package com.binar.mymovieview.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.binar.mymovieview.data.local.userauth.UserRepository

class UserHomeViewModel(private val userRepository: UserRepository): ViewModel() {

    fun getUsername(): LiveData<String> {
        return userRepository.getUsernameValue().asLiveData()
    }
}
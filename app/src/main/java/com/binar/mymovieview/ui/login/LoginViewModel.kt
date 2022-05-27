package com.binar.mymovieview.ui.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.mymovieview.data.local.userauth.UserDataStoreManager
import com.binar.mymovieview.data.local.userauth.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel(private val repository: UserRepository,private val dataStoreManager: UserDataStoreManager): ViewModel() {

    val loginValidation : MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun result():LiveData<Boolean>{
        return loginValidation
    }
    fun reset(){
        loginValidation.value=false
    }

    fun authLogin(email:String,password:String){
        val emailResult = StringBuffer()
        val passwordResult = StringBuffer()
        val usernameResult = StringBuffer()
        viewModelScope.launch(Dispatchers.IO) {
            val result= repository.authLogin(email,password)
            runBlocking(Dispatchers.Main) {
                if (result!=null) {
                    result.let {
                        passwordResult.append(it.password.toString())
                        emailResult.append(it.email.toString())
                        usernameResult.append(it.username)
                    }
                    if (email == emailResult.toString() && password == passwordResult.toString()) {
                        loginValidation.value = true
                        viewModelScope.launch {
                            dataStoreManager.setEmail(email)
                           dataStoreManager.setUsername(usernameResult.toString())
                        }

                    }
                }else{
                    loginValidation.value= false
                }
            }
        }
    }

}
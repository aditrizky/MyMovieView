package com.binar.mymovieview.ui.profile

import android.util.Log
import androidx.lifecycle.*
import com.binar.mymovieview.data.local.userauth.User
import com.binar.mymovieview.data.local.userauth.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileViewModel(private val repository: UserRepository): ViewModel() {
    private val user : MutableLiveData<User> by lazy { MutableLiveData<User>() }
    val updatevalidation : MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun result():LiveData<Boolean>{
        return updatevalidation
    }

    fun resultUser():LiveData<User>{
        return user
    }

     fun getUsername():LiveData<String>{
        return repository.getUsernameValue().asLiveData()
    }
    fun getEmail():LiveData<String>{
        return repository.getEmailValue().asLiveData()
    }

    fun setUsername(username:String){
        viewModelScope.launch {
            repository.setUsername(username)

        }
    }
    fun logout(){
        viewModelScope.launch {
            repository.clearDataStore()
        }
    }


    fun getAllData(username:String){
        val usernameResult = StringBuffer()
        val fullnameResult = StringBuffer()
        val birthdateResult = StringBuffer()
        val addressResult = StringBuffer()
        val imagePath= StringBuffer()
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("usernamecek",username)
         val resultData=repository.getAllData(username)
            runBlocking(Dispatchers.Main) {
                Log.d("usernamecekee",resultData.toString())
                resultData.let {
                    usernameResult.append(it.username.toString())
                    fullnameResult.append(it.fullName.toString())
                    birthdateResult.append(it.birthDate.toString())
                    addressResult.append(it.address.toString())
                    imagePath.append(it.imagePath.toString())
                }
                val resultUser = User(
                    username = usernameResult.toString(),
                    fullName = fullnameResult.toString(),
                    birthDate = birthdateResult.toString(),
                    address = addressResult.toString(),
                    imagePath = imagePath.toString()
                )
               user.value=resultUser
            }
        }
    }

    fun updateData(user:User,email:String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.updateProfile(user,email)
            runBlocking(Dispatchers.Main) {
                if (result != 0 ){
                    updatevalidation.postValue(true)
                }
            }
        }
    }
}
package com.binar.mymovieview.ui.profile

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.binar.mymovieview.data.local.userauth.User
import com.binar.mymovieview.data.local.userauth.UserDataStoreManager
import com.binar.mymovieview.data.local.userauth.UserRepository
import com.binar.mymovieview.utils.ImageHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileViewModel(
    private val repository: UserRepository, private val dataStoreManager: UserDataStoreManager,
    private val imageHelper: ImageHelper
) : ViewModel() {
    private val user: MutableLiveData<User> by lazy { MutableLiveData<User>() }
    val updatevalidation: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun result(): LiveData<Boolean> {
        return updatevalidation
    }

    fun resultUser(): LiveData<User> {
        return user
    }

    fun getEmail(): LiveData<String> {
        return dataStoreManager.getEmailValue().asLiveData()
    }

    fun setUsername(username: String) {
        viewModelScope.launch {
            dataStoreManager.setUsername(username)

        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStoreManager.clearDataStore()
        }
    }

    fun bitmapToUri(bitmap: Bitmap): Uri {
        return imageHelper.getImageUriFromBitmap(bitmap)
    }


    fun getAllData(email: String) {
        val usernameResult = StringBuffer()
        val fullnameResult = StringBuffer()
        val birthdateResult = StringBuffer()
        val addressResult = StringBuffer()
        val imagePath = StringBuffer()
        val emailResult = StringBuffer()
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("usernamecek", email)
            val resultData = repository.getAllData(email)
            runBlocking(Dispatchers.Main) {
                Log.d("usernamecekee", resultData.toString())
                resultData.let {
                    usernameResult.append(it.username.toString())
                    fullnameResult.append(it.fullName.toString())
                    birthdateResult.append(it.birthDate.toString())
                    addressResult.append(it.address.toString())
                    imagePath.append(it.imagePath.toString())
                    emailResult.append(it.email.toString())
                }
                val resultUser = User(
                    username = usernameResult.toString(),
                    fullName = fullnameResult.toString(),
                    birthDate = birthdateResult.toString(),
                    address = addressResult.toString(),
                    imagePath = imagePath.toString(),
                    email = emailResult.toString()
                )
                user.value = resultUser
            }
        }
    }

    fun updateData(user: User, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.updateProfile(user, email)
            Log.d("testt", result.toString())
            runBlocking(Dispatchers.Main) {
                if (result != 0) {
                    updatevalidation.postValue(true)
                    getAllData(email)
                }
            }
        }
    }
}
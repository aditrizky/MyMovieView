package com.binar.mymovieview.mvvm


import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.binar.mymovieview.data.User
import com.binar.mymovieview.room.AplicationDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

class UserAuthViewModel(app: Application): AndroidViewModel(app) {
    private val inEmail: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val inPassword: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val messageHandler =  Handler(Looper.getMainLooper())
    private val sharedPreffile = "kotlinsharedpreferance"
    private var mDB: AplicationDB? = null
    private val context by lazy { getApplication<Application>().applicationContext }
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        sharedPreffile,
        Context.MODE_PRIVATE
    )
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
    private val executor = Executors.newSingleThreadExecutor()
    val email : MutableLiveData<String> by lazy { MutableLiveData<String>()}
    val password : MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val _validation : MutableLiveData<Int> by lazy { MutableLiveData<Int>()}
    val registervalidation : MutableLiveData<Int> by lazy { MutableLiveData<Int>()}
    val username: MutableLiveData<String> by lazy { MutableLiveData<String>()}
    val fullname: MutableLiveData<String> by lazy { MutableLiveData<String>()}
    val birth: MutableLiveData<String> by lazy { MutableLiveData<String>()}
    val address: MutableLiveData<String> by lazy { MutableLiveData<String>()}


    fun getInputan(putEmail:String, putPassword:String){
        inEmail.value=putEmail
        inPassword.value=putPassword
    }
    fun getvalidation():LiveData<Int>{
        return _validation
    }


    fun logout(){
        editor.clear()
        editor.apply()
        _validation.postValue(0)
    }

        fun loginAuth() {
        val emailResult = StringBuffer()
        val passwordResult = StringBuffer()
            val usernameResult = StringBuffer()
        mDB = AplicationDB.getInstance(context)
        viewModelScope.launch(Dispatchers.IO) {
            val user = mDB?.userDao()?.getUsername(inEmail.value)
            Log.d("this", user.toString())
            runBlocking(Dispatchers.Main) {
                user?.forEach {
                   emailResult.append(it.email)
                   passwordResult.append(it.password)
                    usernameResult.append(it.username)
                }
                email.value = emailResult.toString()
                password.value=passwordResult.toString()
                username.value=usernameResult.toString()
                if (inEmail.value==emailResult.toString() && inPassword.value == passwordResult.toString()){
                    Toast.makeText(context,"Login Success",Toast.LENGTH_SHORT).show()
                    _validation.postValue(1)
                    editor.putString("email_key", email.value)
                    editor.putString("username_key", username.value)
                    editor.apply()
                }else{
                    Toast.makeText(context,"Login Failed",Toast.LENGTH_SHORT).show()
                    _validation.postValue(0)
                }
            }
        }
    }

    fun registerUser(user : User, confirmPassword:String){
       mDB = AplicationDB.getInstance(context)
      if (user.password != confirmPassword) {
                Toast.makeText(context, "Confirmation Password Error", Toast.LENGTH_SHORT).show()
            } else {
          viewModelScope.launch(Dispatchers.IO){
              Log.d("auth", "testing")
              val result = mDB?.userDao()?.addUser(user)
              runBlocking(Dispatchers.Main){
                  if (result != 0. toLong()){
                      Toast.makeText(context,"Register Success",Toast.LENGTH_SHORT).show()
                      registervalidation.postValue(1)
                  }else{
                      Toast.makeText(context,"Register Failed",Toast.LENGTH_SHORT).show()
                      registervalidation.postValue(0)
                  }
              }
          }
      }
    }
    fun loginCek(){
        val emailValue = sharedPreferences.getString("email_key", "defaultValue")
        val usernameValue= sharedPreferences.getString("username_key","defaultValue")
        if(emailValue !="defaultValue"){
            _validation.postValue(1)
            username.value= usernameValue
            email.value= emailValue
        }else{
            _validation.value=0
        }
    }
    fun updateUserData(user:User){
        mDB = AplicationDB.getInstance(context)
        viewModelScope.launch(Dispatchers.IO) {
            val result = mDB?.userDao()?.updateData(
                username = user.username,
                email = email.value,
                birthdate = user.birthDate,
                fullname = user.fullName,
                address = user.address
            )
            runBlocking(Dispatchers.Main) {
                if (result != 0){
                    Toast.makeText(context,"Update Success",Toast.LENGTH_SHORT).show()
                    getUserData()
                    editor.putString("username_key", username.value)
                    editor.apply()
                }else{
                    Toast.makeText(context,"Update Failed",Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    fun getUserData() {
        val usernameResult = StringBuffer()
        val fullnameResult = StringBuffer()
        val birthdateResult = StringBuffer()
        val addressResult = StringBuffer()
        mDB = AplicationDB.getInstance(context)
        viewModelScope.launch(Dispatchers.IO) {
            val user = mDB?.userDao()?.getUsername(email.value)
            Log.d("this", user.toString())
            runBlocking(Dispatchers.Main) {
                user?.forEach {
                    usernameResult.append(it.username)
                    fullnameResult.append(it.fullName)
                    birthdateResult.append(it.birthDate)
                    addressResult.append(it.address)

                }
                username.value=usernameResult.toString()
                fullname.value=fullnameResult.toString()
                birth.value=birthdateResult.toString()
                address.value=addressResult.toString()
                editor.putString("username_key", username.value)
                editor.apply()
            }
        }
    }
}
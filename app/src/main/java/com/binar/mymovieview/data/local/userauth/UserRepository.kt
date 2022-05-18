package com.binar.mymovieview.data.local.userauth

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.binar.mymovieview.data.local.AplicationDB
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.ByteArrayOutputStream

class UserRepository(private val userDao: UserDao, private val context: Context) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: UserRepository? = null
        fun getInstance(context: Context): UserRepository? {
            return instance ?: synchronized(UserRepository::class.java) {
                if (instance == null) {
                    val database = AplicationDB.getInstance(context)
                    instance = UserRepository(database!!.userDao(),context)
                }
                return instance
            }
        }
        private const val DATASTORE_NAME = "application_preferences"

        private val USERNAME_KEY = stringPreferencesKey("username_key")
        private val EMAIL_KEY = stringPreferencesKey("email_key")

        private val Context.prefDataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }

    suspend fun registerUser(user: User): Long {
        return userDao.addUser(user)
    }

    suspend fun authLogin(email: String, passwd: String): User {
        return userDao.authLogin(email, passwd)

    }

    suspend fun updateProfile(user: User, email: String): Int {
        return userDao.updateData(
            username = user.username,
            email = email,
            birthdate = user.birthDate,
            fullname = user.fullName,
            address = user.address,
            path = user.imagePath
        )
    }
    suspend fun getAllData(username: String?): User {
        return userDao.getAllData(username)
    }



//preferences data store
    suspend fun setUsername(username: String){
        context.prefDataStore.edit {
            it[USERNAME_KEY]=username
        }
    }

    suspend fun setEmail(email: String){
        context.prefDataStore.edit {
            it[EMAIL_KEY]=email
        }
    }

    fun getUsernameValue(): Flow<String>{
        return context.prefDataStore.data.map {
            it[USERNAME_KEY]?: "default"
        }
    }
    fun getEmailValue(): Flow<String>{
        return context.prefDataStore.data.map {
            it[EMAIL_KEY]?: "default"
        }
    }

    suspend fun clearDataStore(){
        context.prefDataStore.edit {
            it.clear()
        }
    }
    fun getImageUriFromBitmap(bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path.toString())
    }


}

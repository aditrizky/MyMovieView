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
    suspend fun getAllData(email: String?): User {
        return userDao.getAllData(email)
    }

}

package com.binar.mymovieview.data.local.userauth


import android.graphics.Bitmap
import androidx.room.*

@Dao
interface UserDao {
    @Insert
    suspend fun addUser(user: User): Long

    @Query("SELECT * FROM user WHERE email = :email AND password= :password")
    suspend fun authLogin(email:String?= null,password:String?= null): User

    @Query("UPDATE User SET username= :username ,fullName = :fullname ,birthDate= :birthdate ,address = :address,imagePath= :path WHERE email= :email")
    suspend fun updateData(email:String?= null, username:String?= null,birthdate:String?= null,fullname:String?= null,address:String?= null,path:String?= null) : Int

    @Query("SELECT * FROM user WHERE email= :email")
    suspend fun getAllData(email: String?=null): User

}
package com.binar.mymovieview.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.binar.mymovieview.data.local.favorite.FavoriteDao
import com.binar.mymovieview.data.local.favorite.FavoriteMovie
import com.binar.mymovieview.data.local.userauth.User
import com.binar.mymovieview.data.local.userauth.UserDao

@Database(entities = [User::class,FavoriteMovie::class], version = 1,exportSchema = false)
abstract class AplicationDB: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun favoriteDao(): FavoriteDao

}
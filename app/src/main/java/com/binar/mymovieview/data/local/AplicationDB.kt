package com.binar.mymovieview.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.binar.mymovieview.data.local.favorite.FavoriteDao
import com.binar.mymovieview.data.local.favorite.FavoriteMovie
import com.binar.mymovieview.data.local.userauth.User
import com.binar.mymovieview.data.local.userauth.UserDao

@Database(entities = [User::class,FavoriteMovie::class], version = 1)
abstract class AplicationDB: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun favoriteDao(): FavoriteDao

    companion object{
        private var instance : AplicationDB?=null
        fun getInstance(context: Context): AplicationDB?{
            if (instance ==null){
                synchronized(AplicationDB::class){
                    instance = Room.databaseBuilder(context.applicationContext,
                        AplicationDB::class.java,"MyMovieViewApplication.db").build()
                }
            }
            return instance
        }
        fun destroyInstance(){
            instance = null
        }
    }
}
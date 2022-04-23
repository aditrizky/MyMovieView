package com.binar.mymovieview.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.binar.mymovieview.data.User

@Database(entities = [User::class], version = 1)
abstract class AplicationDB: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object{
        private var instance : AplicationDB?=null
        fun getInstance(context: Context):AplicationDB?{
            if (instance==null){
                synchronized(AplicationDB::class){
                    instance= Room.databaseBuilder(context.applicationContext,AplicationDB::class.java,"MyMovieView.db").build()
                }
            }
            return instance
        }
        fun destroyInstance(){
            instance= null
        }
    }
}
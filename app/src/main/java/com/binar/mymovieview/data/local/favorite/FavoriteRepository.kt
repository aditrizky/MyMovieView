package com.binar.mymovieview.data.local.favorite

import android.annotation.SuppressLint
import android.content.Context
import com.binar.mymovieview.data.local.AplicationDB


class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: FavoriteRepository? = null
        fun getInstance(context: Context): FavoriteRepository? {
            return instance ?: synchronized(FavoriteRepository::class.java) {
                if (instance == null) {
                    val database = AplicationDB.getInstance(context)
                    instance = FavoriteRepository(database!!.favoriteDao())
                }
                return instance
            }
        }
    }
    suspend fun addFavorite(favoriteMovie: FavoriteMovie):Long{
        return favoriteDao.addFavorite(favoriteMovie)
    }

    suspend fun deleteFavorite(favoriteMovie: FavoriteMovie):Int{
        return favoriteDao.deleteFavorite(favoriteMovie)
    }

    suspend fun getAllFavorite():List<FavoriteMovie>{
        return favoriteDao.getAllFavorite()
    }


}
package com.binar.mymovieview.data.local.favorite

import android.annotation.SuppressLint
import android.content.Context
import com.binar.mymovieview.data.local.AplicationDB


class FavoriteRepository(private val favoriteDao: FavoriteDao) {


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
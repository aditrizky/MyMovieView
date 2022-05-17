package com.binar.mymovieview.data.local.favorite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert
    suspend fun addFavorite(favoriteMovie: FavoriteMovie):Long

    @Delete
    suspend fun deleteFavorite(favoriteMovie: FavoriteMovie):Int

    @Query("SELECT * FROM FavoriteMovie")
    suspend fun getAllFavorite(): List<FavoriteMovie>


}
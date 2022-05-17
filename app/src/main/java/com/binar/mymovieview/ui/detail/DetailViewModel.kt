package com.binar.mymovieview.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.mymovieview.data.local.favorite.FavoriteMovie
import com.binar.mymovieview.data.local.favorite.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DetailViewModel(private val repository: FavoriteRepository):ViewModel() {
    val addvalidation : MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun result(): LiveData<Boolean> {
        return addvalidation
    }


    fun addFavorite(favoriteMovie: FavoriteMovie){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.addFavorite(favoriteMovie)
            runBlocking(Dispatchers.Main) {
                if(result != 0 .toLong()){
                    addvalidation.postValue(true)
                }
            }
        }
    }
}
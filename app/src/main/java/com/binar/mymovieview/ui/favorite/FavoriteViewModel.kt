package com.binar.mymovieview.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.mymovieview.data.local.favorite.FavoriteMovie
import com.binar.mymovieview.data.local.favorite.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FavoriteViewModel(private val repository: FavoriteRepository):ViewModel() {

        private val movie : MutableLiveData<List<FavoriteMovie>> by lazy {
            MutableLiveData<List<FavoriteMovie>>().also {

            }
        }

    fun getMovie(): LiveData<List<FavoriteMovie>> {
        return movie
    }


    fun getAllFavorite(){
        viewModelScope.launch (Dispatchers.IO){
            val result = repository.getAllFavorite()
            runBlocking(Dispatchers.Main) {
                movie.value= result
            }
        }

    }
}
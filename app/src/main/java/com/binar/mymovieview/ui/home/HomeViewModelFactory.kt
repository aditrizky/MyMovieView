package com.binar.mymovieview.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binar.mymovieview.data.local.userauth.UserRepository
import com.binar.mymovieview.data.remote.model.populer.MoviesRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import java.lang.IllegalArgumentException

@DelicateCoroutinesApi
class HomeViewModelFactory constructor(private val moviesRepository: MoviesRepository):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(moviesRepository) as T
        }
        throw IllegalArgumentException("unknown view model")
    }

}
package com.binar.mymovieview.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.binar.mymovieview.data.local.userauth.UserDataStoreManager
import com.binar.mymovieview.data.remote.model.populer.MoviesRepository
import com.binar.mymovieview.data.remote.model.populer.MoviesRemoteDataSource
import com.binar.mymovieview.data.remote.model.populer.model.Result
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class HomeViewModel(private  val repository: MoviesRepository,
                    private val dataStoreManager: UserDataStoreManager): ViewModel() {
    private val movie : MutableLiveData<List<Result>> by lazy {
        MutableLiveData<List<Result>>().also {
            loadMovie()
        }
    }

    private val respons: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>().also {
            loadMovie()
        }
    }

    fun getCode(): LiveData<Int> {
        return respons
    }

    fun getMovie(): LiveData<List<Result>>{
        return movie
    }

    private fun loadMovie() {

       repository.getMovies(object : MoviesRemoteDataSource.MovieCallBack{
           override fun onCompllete(result: List<Result>, code: Int) {
               movie.value=result
               respons.value=code
           }

       })


    }
    fun getUsername(): LiveData<String> {
        return dataStoreManager.getUsernameValue().asLiveData()
    }


}
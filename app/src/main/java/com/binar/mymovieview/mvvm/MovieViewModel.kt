package com.binar.mymovieview.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binar.mymovieview.response.GetMovieResponse
import com.binar.mymovieview.response.Result
import com.binar.mymovieview.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel: ViewModel() {
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
        ApiClient.instance.getAllMovie()
            .enqueue(object: Callback<GetMovieResponse>{
                override fun onResponse(
                    call: Call<GetMovieResponse>,
                    response: Response<GetMovieResponse>
                ) {
                    movie.value=response.body()?.results
                    respons.value=response.code()
                }

                override fun onFailure(call: Call<GetMovieResponse>, t: Throwable) {
                    Log.d("moview",t.message.toString())
                }

            })
    }


}
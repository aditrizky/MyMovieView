package com.binar.mymovieview.data.remote.model.populer


import com.binar.mymovieview.response.Result
import com.binar.mymovieview.service.ApiClient
import kotlinx.coroutines.*


class MoviesRemoteDataSource {
    @DelicateCoroutinesApi
    fun getMovies(movieCallBack: MovieCallBack): List<Result>{
        GlobalScope.launch(Dispatchers.IO){
            val response = ApiClient.instance.getAllMovie()
            runBlocking(Dispatchers.Main) {
                if (response.isSuccessful){
                    val result= response.body()
                    val code = response.code()
                    movieCallBack.onCompllete(result!!.results,code)
                }
            }

        }
        return emptyList()
    }
    interface MovieCallBack{
        fun onCompllete(result:List<Result>,code:Int)

    }
}
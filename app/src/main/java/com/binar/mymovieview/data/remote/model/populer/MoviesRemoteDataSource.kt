package com.binar.mymovieview.data.remote.model.populer


import com.binar.mymovieview.data.remote.model.populer.model.Result
import com.binar.mymovieview.service.ApiService
import kotlinx.coroutines.*


class MoviesRemoteDataSource(private val apiService: ApiService) {
    @DelicateCoroutinesApi
    fun getMovies(movieCallBack: MovieCallBack): List<Result>{
        GlobalScope.launch(Dispatchers.IO){
            val response = apiService.getAllMovie()
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
        fun onCompllete(result:List<Result>, code:Int)

    }
}
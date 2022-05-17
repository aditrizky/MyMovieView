package com.binar.mymovieview.data.remote.model.populer

import com.binar.mymovieview.response.Result
import kotlinx.coroutines.DelicateCoroutinesApi

class MoviesRepository constructor(private  val moviesRemoteDataSource: MoviesRemoteDataSource) {

    @DelicateCoroutinesApi
    fun getMovies(movieCallBack: MoviesRemoteDataSource.MovieCallBack): List<Result> {
        return moviesRemoteDataSource.getMovies(movieCallBack)
    }
}
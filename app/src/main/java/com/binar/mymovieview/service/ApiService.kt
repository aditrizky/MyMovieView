package com.binar.mymovieview.service

import com.binar.mymovieview.response.GetMovieResponse
import retrofit2.Call
import retrofit2.http.GET


interface ApiService {
    @GET("3/movie/popular?api_key=cdcabd44843b55fe6c52244983f13228&language=en-US")
    fun getAllMovie(): Call<GetMovieResponse>
}
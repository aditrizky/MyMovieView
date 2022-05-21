package com.binar.mymovieview.service

import com.binar.mymovieview.data.remote.model.populer.model.GetMovieResponse
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {
    @GET("3/movie/popular?api_key=cdcabd44843b55fe6c52244983f13228&language=en-US")
    suspend fun getAllMovie(): Response<GetMovieResponse>
}
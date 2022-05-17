package com.binar.mymovieview

import android.app.Application
import android.content.Context
import com.binar.mymovieview.data.local.userauth.UserRepository
import com.binar.mymovieview.data.remote.model.populer.MoviesRepository
import com.binar.mymovieview.data.remote.model.populer.MoviesRemoteDataSource

class MyApplication: Application() {
    private val remoteDataSource by lazy { MoviesRemoteDataSource() }
    val repository by lazy { MoviesRepository(remoteDataSource) }


}
package com.binar.mymovieview

import android.app.Application
import com.binar.mymovieview.di.*
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.android.ext.koin.androidContext

import org.koin.core.context.startKoin


@DelicateCoroutinesApi
class MyApplication : Application() {
    /* private val remoteDataSource by lazy { MoviesRemoteDataSource() }
     val repository by lazy { MoviesRepository(remoteDataSource) }*/
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    localModule,
                    networkModul,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                    dataStoreModule,
                    imageHelperModule
                )
            )
        }
    }


}
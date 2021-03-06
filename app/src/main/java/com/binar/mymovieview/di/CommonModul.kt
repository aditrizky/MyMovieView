package com.binar.mymovieview.di


import androidx.room.Room
import com.binar.mymovieview.data.local.AplicationDB
import com.binar.mymovieview.data.local.userauth.UserDataStoreManager
import com.binar.mymovieview.data.remote.model.populer.MoviesRemoteDataSource
import com.binar.mymovieview.data.remote.model.populer.MoviesRepository
import com.binar.mymovieview.service.ApiService
import com.binar.mymovieview.utils.ImageHelper
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val localModule = module {
    factory { get<AplicationDB>().userDao() }
    factory { get<AplicationDB>().favoriteDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            AplicationDB::class.java,
            "MyMovieViewApplication.db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}

val networkModul = module {
    single {
        ChuckerInterceptor.Builder(androidContext())
            .collector(ChuckerCollector(androidContext()))
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()

    }
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val chuckerInterceptor: ChuckerInterceptor = get()
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(chuckerInterceptor)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }


}
val moviesRemoteDataSourceModule = module {
    single {
        MoviesRemoteDataSource(get())
    }
}

val dataStoreModule = module {
    single { UserDataStoreManager(androidContext()) }
}

val imageHelperModule = module {
    single { ImageHelper(androidContext()) }
}

